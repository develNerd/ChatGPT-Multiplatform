package org.flepper.chatgpt.android.presentation.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okio.IOException
import org.flepper.chatgpt.data.usecases.BaseUseCase
import org.flepper.chatgpt.data.usecases.ChatBaseUseCase
import org.flepper.chatgpt.data.usecases.StateFlowBaseUseCase


abstract class BaseViewModel : ViewModel() {

    data class UIResult<out T>(val result:T?,val isLoaded:Boolean,val error:String? = null)

    fun <Input,Output:Any> executeUseCase(
        inputValue:Input,
        useCase: BaseUseCase<Input, Output>,
        callback: (Output) -> Unit = {}){
        viewModelScope.launch(Dispatchers.IO) {
            callback(useCase.dispatch(inputValue))
        }
    }


    suspend fun <Input,Output:Any> executeValueUseCase(
        inputValue:Input,
        useCase: BaseUseCase<Input, Output>):Output{
        return useCase.dispatch(inputValue)
    }

    fun <Input,Output:Any> executeStreamUseCase(
        inputValue:Input,
        useCase: ChatBaseUseCase<Input, Output>,
        callback: (Output) -> Unit = {}){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.dispatch(inputValue).collectLatest {
                Log.e("Use CaseContent", it.toString())
                callback(it)
            }
        }
    }

    fun <Input,Output:Any> executeApiUseCase(
        inputValue:Input,
        useCase: StateFlowBaseUseCase<Input, Output>,
        callback: (UIResult<Output>) -> Unit = {}){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.dispatch(inputValue).collectLatest {result ->
                if(result.error != null){
                    Log.e("ERROR OCCURRED", result.error!!.localizedMessage ?: "")
                    val message = when(result.error){
                        is IOException -> result.error?.message
                        else -> "SOMETHING_WENT_WRONG"
                    }
                    callback(UIResult(result.result,result.isLoaded,message))
                }else {
                    callback(UIResult(result.result,result.isLoaded,null))
                }
            }
        }
    }

}