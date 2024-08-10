package articles.presentation.intent

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UIEvent, State : UIState, Effect : UIEffect> : ScreenModel {
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    protected val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState) //Mutable state
    val uiState = _uiState.asStateFlow() //Immutable state

    private val _uiEvent: MutableSharedFlow<Event> = MutableSharedFlow() //Mutable Event
    val uiEvent = _uiEvent.asSharedFlow() // Immutable event

    private val _uiEffect: Channel<Effect> = Channel() //Mutable effect
    val uiEffect = _uiEffect.receiveAsFlow()// Immutable effect

    init {
        subscribeEvents()
    }

    /**
     * To subscribe and listen for the UI events
     */
    private fun subscribeEvents() {
        screenModelScope.launch {
            uiEvent.collect {
                handleEvent(it)
            }
        }
    }

    /**
     * To handle each event
     */
    abstract fun handleEvent(uiEvent: UIEvent)

    /**
     * Sets the new UI event
     * @param event UI event
     */
    fun setEvent(event: Event) {
        val newEvent = event
        screenModelScope.launch {
            _uiEvent.emit(newEvent)
        }
    }

    /**
     * Sets the new UI state
     * @param reduce The extension function type of State. This can be called on the currentState instance
     */
    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    /**
     * Sets the new UI effect
     * @param effect The function type that returns a UI effect
     */
    protected fun setEffect(effect: () -> Effect) {
        val newEffect = effect()
        screenModelScope.launch {
            _uiEffect.send(newEffect)
        }
    }
}