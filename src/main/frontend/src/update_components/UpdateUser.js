import React, {Component } from "react";
import axios from "axios";
import {Link, withRouter} from "react-router-dom";
import Button from "@material-ui/core/Button";
import {Input} from "@material-ui/core";
const axiosPOSTconfig = {headers: {'Content-Type': 'application/json'}};



class UpdateUser extends Component{

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            username: '',
            password: ''
        };
    }

    onChange = (event) => {
            this.setState({[event.target.id]: event.target.value});
    }

    onSubmit = (event) => {
        event.preventDefault();
        let {email, username, password} = this.state;
        if((email === '') || (username === '') || (password === '')){
            alert('You need Enter all Fields');
        }
        else{
            axios.post(`http://localhost:8082/users/update/` + this.props.match.params.id, JSON.stringify({
                'email': email,
                'username': username,
                'password': password,
            }), axiosPOSTconfig)
                .then((response) => {
                    alert('Update Completed');
                    this.setState({status: response.data.status});
                })
                .catch((error) => {console.log(error)});
        }

    }

    componentDidMount() {
        console.log(this.props);
        axios.get(`http://localhost:8082/users/update/`+this.props.match.params.id)
            .then((response) => {this.setState({email: response.data.data.email, username: response.data.data.username, password: response.data.data.password});})
            .catch((error) => {console.log(error); this.setState({ message: error.message.details })});
    }

    render() {
        let {email, username, password} = this.state;
        return(
            <main role="main" className="container">
                <div>
                    <form onSubmit={this.onSubmit}>
                        <Input id="email" type="text" value={email} placeholder={"Email"} onChange={this.onChange}/><br/>
                        <Input id="username" type="text" value={username} placeholder={"User Name"} onChange={this.onChange}/><br/>
                        <Input id="password" type="text" value={password} placeholder={"Password"} onChange={this.onChange}/><br/>
                        <br/>
                        <Button onClick={this.onSubmit} variant="contained" color="primary">Update</Button><br/>
                        <br/><Button component={Link} to="/Users" variant="contained" color="primary">User's Table</Button>
                    </form>
                </div>
            </main>
        );
    }
}

export default withRouter(UpdateUser);
