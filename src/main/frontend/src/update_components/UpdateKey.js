import React, {Component} from "react";
import axios from "axios";
import {Link, withRouter} from "react-router-dom";
import Button from "@material-ui/core/Button";
import {TextField} from "@material-ui/core";
const axiosPOSTconfig = {headers: {'Content-Type': 'application/json'}};


class UpdateKey extends Component{

    constructor(props) {
        super(props);
        this.state = {
            id: '',
            key: "",
            game: ""
        };
    }

    onChange = (event) => {
        this.setState({[event.target.id]: event.target.value});
    }

    onSubmit = (event) => {
        event.preventDefault();
        let {key, game} = this.state;
        if((key === '') || (game === '')) {
            alert('Enter all Fields');
        }
        else{
            axios.post('http://localhost:8082/keys/update/' + this.props.match.params.id, JSON.stringify({
                'id': this.props.match.params.id,
                'key': key,
                'game': game,
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
        axios.get(`http://localhost:8082/keys/update/`+this.props.match.params.id)
            .then((response) => {this.setState({key: response.data.data.key, game: response.data.data.game.name});})
            .catch((error) => {console.log(error); this.setState({ message: error.message })});
    }

    render() {
        let {key, game} = this.state;
        return(
            <main role="main" className="container">
                <div>
                    <form onSubmit={this.onSubmit}>
                        <TextField id="key" type="text" value={key} placeholder={"Key"} onChange={this.onChange}/><br/>
                        <TextField id="game" type="text" value={game} placeholder={"Game name"} onChange={this.onChange}/><br/>

                        <br/><Button onClick={this.onSubmit} variant="contained" color="primary">Update Key</Button><br/>
                        <br/><Button component={Link} to="/Keys" variant="contained" color="primary">Key's Table</Button><br/>
                    </form>
                </div>
            </main>
        );
    }
}

export default withRouter(UpdateKey);
