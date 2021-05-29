import React, {Component} from "react";
import { Button, TextField } from '@material-ui/core';
import axios from 'axios';
import {Link, withRouter} from "react-router-dom";
const axiosPOSTconfig = {headers: {'Content-Type': 'application/json'}};


class CreateKey extends Component{

    constructor(props) {
        super(props)
        this.state = {
            key: "",
            game: ""
        }
    }

    onChange = (e) => {
        this.setState({[e.target.id]: e.target.value});
    }

    onSubmit = (event) => {
        let {key, game} = this.state;
        event.preventDefault();
        if(key === '' || game === ''){
            alert('Enter all fields');
        }
        else{
            axios.post('http://localhost:8082/keys/create', JSON.stringify({
                'key': key,
                'game': game,
            }), axiosPOSTconfig)
                .then((response) => {
                    this.setState({status: response.data.status});
                    alert('Creating completed');
                })
                .catch((error) => {console.log(error)});
        }
    }

    render() {
        let {key, game} = this.state;
        return(
            <main>
                <div>
                    <form onSubmit={this.onSubmit}>
                        <TextField id="key" type="text" value={key} placeholder={"Key"} onChange={this.onChange}/><br/>
                        <TextField id="game" type="text" value={game} placeholder={"Game name"} onChange={this.onChange}/><br/>

                        <br/><Button onClick={this.onSubmit} variant="contained" color="primary">Create Key</Button><br/>
                        <br/><Button component={Link} to="/Keys" variant="contained" color="primary">Key's Table</Button>
                    </form>
                </div>
            </main>
        );
    }
}
export default withRouter(CreateKey);
