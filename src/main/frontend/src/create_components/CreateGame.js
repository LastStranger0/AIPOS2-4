import React, {Component} from "react";
import { Button, TextField } from '@material-ui/core';
import axios from 'axios';
import {Link, withRouter} from "react-router-dom";
const axiosPOSTconfig = {headers: {'Content-Type': 'application/json'}};


class CreateGame extends Component{

    constructor(props) {
        super(props)
        this.state = {
            name: "",
            price: "",
            description: "",
            date: ""
        }
    }

    onChange = (event) => {
        this.setState({[event.target.id]: event.target.value});
    }

    onSubmit = (event) => {
        event.preventDefault();
        let {name, price, description, date} = this.state;
        if(name === '' || price === '' || description === '' || date === ''){
            alert('Enter all Fields');
        }
        else{
            axios.post('http://localhost:8082/games/create', JSON.stringify({
                'name': name,
                'price': price,
                'description': description,
                'date': date,
            }), axiosPOSTconfig)
                .then((response) => {
                    this.setState({status: response.data.status});
                    alert('Creating completed');
                })
                .catch((error) => {console.log(error) || alert(error)});
        }
    }

    render() {
        let {name, price, description, date} = this.state;
        return(
            <main>
                <div>
                    <form onSubmit={this.onSubmit}>
                        <TextField id="name" type="text" value={name} placeholder={"Name"} onChange={this.onChange}/><br/>
                        <TextField id="price" type="text" value={price} placeholder={"Price"} onChange={this.onChange}/><br/>
                        <TextField id="description" type="text" value={description} placeholder={"Description"} onChange={this.onChange}/><br/>
                        <TextField id="date" type="text" value={date} placeholder={"Date"} onChange={this.onChange}/><br/>

                        <br/><Button onClick={this.onSubmit} variant="contained" color="primary">Create Game</Button><br/>
                        <br/><Button component={Link} to="/Image" variant="contained" color="primary" >Add Image</Button><br/>
                        <br/><Button component={Link} to="/Games" variant="contained" color="primary" >Game's Table</Button><br/>
                    </form>
                </div>
            </main>
        );
    }
}
export default withRouter(CreateGame);
