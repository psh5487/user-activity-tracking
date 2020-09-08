import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import App from './App';

const userInTime = new Date();

ReactDOM.render(
    <App userId={'shopad'} userInTime={userInTime}/>, document.getElementById('root')
);