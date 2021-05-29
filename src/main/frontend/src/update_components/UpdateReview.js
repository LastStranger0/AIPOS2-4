import React, {Component} from "react";
import axios from "axios";
import {Link, withRouter} from "react-router-dom";
import Button from "@material-ui/core/Button";
import {TextField} from "@material-ui/core";
const axiosPOSTconfig = {headers: {'Content-Type': 'application/json'}};


class UpdateReview extends Component{

    constructor(props) {
        super(props);
        this.state = {
            author: '',
            game: '',
            review: ''
        };
    }

    onChange = (event) => {
        this.setState({[event.target.id]: event.target.value});
    }

    onSubmit = (event) => {
        event.preventDefault();
        let {author, game, review} = this.state;
        if((author === '') || (game === '') || (review === '')){
            alert('Enter all Fields');
        }
        else{
            axios.post(`http://localhost:8082/reviews/update/` + this.props.match.params.id, JSON.stringify({
                'author': author,
                'game': game,
                'review': review,
                'id': this.props.match.params.id,
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
        axios.get(`http://localhost:8082/reviews/update/`+this.props.match.params.id)
            .then((response) => {this.setState({author: response.data.data.author, game: response.data.data.game, review: response.data.data.review});})
            .catch((error) => {console.log(error); this.setState({ message: error.message })});
    }

    render() {
        let {author, game, review} = this.state;
        return(
            <main role="main" className="container">
                <div>
                    <form onSubmit={this.onSubmit}>
                        <TextField id="author" type="text" value={author} placeholder={"Author"} onChange={this.onChange}/><br/>
                        <TextField id="game" type="text" value={game} placeholder={"Game"} onChange={this.onChange}/><br/>
                        <TextField id="review" type="text" value={review} placeholder={"Review"} onChange={this.onChange}/><br/>

                        <br/><Button onClick={this.onSubmit} variant="contained" color="primary">Update Review</Button><br/>
                        <br/><Button component={Link} to="/Review" variant="contained" color="primary">Review's Table</Button>
                    </form>
                </div>
            </main>
        );
    }
}

export default withRouter(UpdateReview);
