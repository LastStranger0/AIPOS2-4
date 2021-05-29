import React, {Component} from "react";
import axios from "axios";
import {Link, withRouter} from "react-router-dom";
import Button from "@material-ui/core/Button";
import {Avatar} from "@material-ui/core";

export class Games extends Component{

    constructor(props) {
        super(props);
        this.state = {
            rows: []
        };
    }

    componentDidMount() {
        axios.get('http://localhost:8082/games')
            .then((response => {this.setState({rows: response.data.data});}))
            .catch((error) => {console.log(error); this.setState({ message: error.message })});
    }

    render() {
        return (
            <main role="main" className="container">
                <div align="center">
                    {this.state.rows === null && <p>Loading menu...</p>}
                    <div>
                        <Button component={Link} to="/CreateGame" variant="contained" color="primary">Add Game</Button>
                    </div>
                    <br/>
                    <table className="games">
                        <thead>
                            <tr>
                                <th width={50}>id</th>
                                <th width={100}>Date</th>
                                <th width={500}>Description</th>
                                <th width={200}>Name</th>
                                <th width={50}>Price</th>
                                <th width={50}>Image</th>
                                <th width={50}>Action</th>
                            </tr>
                        </thead>
                        {this.state.rows && this.state.rows.map(game => (
                            <tbody align="center">
                                <tr>
                                    <td width={50}>{game.id}</td>
                                    <td width={100}>{game.date}</td>
                                    <td width={500}>{game.description}</td>
                                    <td width={200}>{game.name}</td>
                                    <td width={50}>{game.price}</td>
                                    <td width={50}><Avatar alt="Remy Sharp" src={game.linkMainImage}/></td>
                                    <Button component={Link} to={'/games/delete/' + game.id} variant="contained" color="primary">Delete</Button>
                                    <Button component={Link} to={'/games/update/' + game.id} variant="contained" color="primary">Update</Button>
                                </tr>
                            </tbody>
                        ))}
                    </table>
                </div>
            </main>
        );
    }
}
export default withRouter(Games);

