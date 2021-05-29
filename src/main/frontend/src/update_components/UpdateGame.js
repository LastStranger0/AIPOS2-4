import React, {Component} from "react";
import axios from "axios";
import {Link, withRouter} from "react-router-dom";
import Button from "@material-ui/core/Button";
import {TextField} from "@material-ui/core";
const axiosPOSTconfig = {headers: {'Content-Type': 'application/json'}};


class UpdateGame extends Component{

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            price: "",
            description: "",
            date: ""
        };
    }

    onChange = (event) => {
            this.setState({[event.target.id]: event.target.value});
    }

    onSubmit = (event) => {
        let {game, price, description, date} = this.state;
        event.preventDefault();
        if(game === '', price === '', description === '', date === ''){
            alert("Enter all Fields");
        }
        else{
            axios.post(`http://localhost:8082/games/update/` + this.props.match.params.id, JSON.stringify({
                'id': this.props.match.params.id,
                'name': game,
                'price': price,
                'description': description,
                'date': date,
            }), axiosPOSTconfig)
                .then((response) => {
                    alert('Update Completed');
                    this.setState({status: response.data.status});
                })
                .catch((error) => {console.log(error)});
        }

    }

    componentDidMount() {
        axios.get(`http://localhost:8082/games/update/`+this.props.match.params.id)
            .then((response) => {this.setState({id: this.props.match.params.id, date: response.data.data.date, description: response.data.data.description, name: response.data.data.name, price: response.data.data.price});})
            .catch((error) => {console.log(error); this.setState({ message: error.message })});
    }

    render() {
        let {game, price, description, date} = this.state;
        return(
            <main role="main" className="container">
                <div>
                        <form onSubmit={this.onSubmit}>
                            <TextField id="name" type="text" value={game} placeholder={"Enter new Name"} onChange={this.onChange}/><br/>
                            <TextField id="price" type="text" value={price} placeholder={"Enter new Price"} onChange={this.onChange}/><br/>
                            <TextField id="description" type="text" value={description} placeholder={"Enter new Description"} onChange={this.onChange}/><br/>
                            <TextField id="date" type="text" value={date} placeholder={"Enter new Date"} onChange={this.onChange}/><br/>

                            <br/><Button onClick={this.onSubmit} variant="contained" color="primary">Update Game</Button><br/>
                            <br/><Button component={Link} to="/Image" variant="contained" color="primary" >Add Image</Button>
                            <Button component={Link} to="/Games" variant="contained" color="primary" >Game's Table</Button><br/>
                        </form>
                </div>
            </main>
        );
    }
}

export default withRouter(UpdateGame);
