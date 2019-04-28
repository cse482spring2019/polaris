import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import InputForm from '../presentational/InputForm';

class InputFormContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            title: ''
        }
    }

    render() {
        return (
            <form id='article form'>
                <InputForm
                    label={'title'}
                    text={'Title'}
                    type={'text'}
                    id={'title'}
                    value={this.state.title}
                    handleChange={(e) => this.setState({[e.target.id]: e.target.value})}
                />
            </form>
        );
    }
}

export default InputFormContainer;
