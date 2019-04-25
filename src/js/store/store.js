import { createStore } from 'redux';
import RootReducer from '../reducers/RootReducer.js'

// action : signal to store that state needs to change
// reducer: js function with current state and action and creates a new state
const store = createStore(RootReducer);

export default store;
