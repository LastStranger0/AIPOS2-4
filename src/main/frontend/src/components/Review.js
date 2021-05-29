import React, {Component} from "react";
import axios from "axios";
import {Link, withRouter} from "react-router-dom";
import Button from "@material-ui/core/Button";

class Review extends Component{
    constructor(props) {
        super(props);
        this.state = {
            rows: []
        };
    }

    componentDidMount() {
        axios.get('http://localhost:8082/reviews')
            .then((response) => {this.setState({rows: response.data.data});})
            .catch((error) => {console.log(error); this.setState({ message: error.message })});
    }

    render() {
        return (
            <main role="main" className="container">
                <div align="center">
                    {this.state.rows === null && <p>Loading menu...</p>}
                    <table className="reviews">
                        <thead>
                            <tr>
                                <th width={50}>Id</th>
                                <th width={100}>Data</th>
                                <th width={500}>Reviews</th>
                                <th width={200}>Author</th>
                                <th width={200}>Game</th>
                                <th width={50}>Action</th>
                            </tr>
                        </thead>
                        {this.state.rows && this.state.rows.map(reviews => (
                            <tbody align="center">
                                <tr>
                                    <td width={50}>{reviews.id}</td>
                                    <td width={100}>{reviews.date}</td>
                                    <td width={500}>{reviews.review}</td>
                                    <td width={200}>{reviews.author.username}</td>
                                    <td width={200}>{reviews.game.name}</td>
                                    <Button component={Link} to={'/reviews/delete/' + reviews.id} variant="contained" color="primary">Delete</Button>
                                    <Button component={Link} to={'/reviews/update/' + reviews.id} variant="contained" color="primary">Update</Button>
                                </tr>
                            </tbody>
                        ))}
                    </table>
                    <div>
                        <Button component={Link} to="/CreateReview" variant="contained" color="primary">Add Review</Button>
                        <Button component={Link} to="/DeleteReview" variant="contained" color="primary">Delete Review</Button>
                    </div>
                </div>
            </main>
        );
    }
}
export default withRouter(Review);
