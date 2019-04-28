import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import PropTypes from 'prop-types';

class InputForm extends Compnent {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={'form-group'}>
                <label htmlForm={this.props.label}>
                    {this.props.text}
                </label>

                <input
                    type={this.props.type}
                    className={'form-control'}
                    id={this.props.id}
                    value={this.props.value}
                    onChange={this.props.handleChange}
                    required
                    />
            </div>
        );
    }
}

InputForm.propTypes = {
    label: PropTypes.string.isRequired,
    text: PropTypes.string.isRequired,
    type: PropTypes.string.isRequired,
    id: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired,
    handleChange: PropTypes.func.isRequired
}

export default InputForm;
