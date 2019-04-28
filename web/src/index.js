// import InputFormContainer from './js/components/container/InputFormContainer';
import ReactDOM from 'react-dom';
import React from 'react';
import { Provider } from 'react-redux';
import store from './js/store/store';
import App from './js/component/App.js';

const render = (
    <Provider store={store}>
        <App/>
    </Provider>
);

const wrapper = document.getElementById('create-article-form');
wrapper ? ReactDOM.render(render, wrapper) : null;


