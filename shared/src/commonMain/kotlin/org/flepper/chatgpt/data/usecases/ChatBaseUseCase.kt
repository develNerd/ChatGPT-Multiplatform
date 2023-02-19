package org.flepper.chatgpt.data.usecases

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.flepper.chatgpt.data.network.OnResultObtained

abstract class ChatBaseUseCase<in Input,out T>(){
    abstract suspend fun dispatch(input: Input):SharedFlow<T>
}

abstract class BaseUseCase<in Input,out T>(){
    abstract suspend fun dispatch(input: Input):T
}

abstract class StateFlowBaseUseCase<in Input,out T>(){
    abstract suspend fun dispatch(input: Input):StateFlowResult<T>
}



typealias StateFlowResult<T> = StateFlow<OnResultObtained<T>>