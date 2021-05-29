import React, {Component} from "react";
import axios from "axios";
import {Link, withRouter} from "react-router-dom";
import Button from "@material-ui/core/Button";

class Keys extends Component{
    constructor(props) {
        super(props);
        this.state = {
            rows: []
        };
    }

    componentDidMount() {
        axios.get('http://localhost:8082/keys')
            .then((response) => {this.setState({rows: response.data.data});})
            .catch((error) => {console.log(error); this.setState({ message: error.message })});
    }

    render() {
        return (
            <main role="main" className="container">
                <div align="center">
                    {this.state.rows === null && <p>Loading menu...</p>}
                    <table className="keys">
                        <thead>
                            <tr>
                                <th width={50}>Id</th>
                                <th width={300}>Key</th>
                                <th width={200}>Game</th>
                                <th width={50}>Action</th>

                            </tr>
                        </thead>
                        {this.state.rows && this.state.rows.map(key => (
                            <tbody align="center">
                                <tr>
                                    <td width={50}>{key.id}</td>
                                    <td width={300}>{key.key}</td>
                                    <td width={200}>{key.game.name}</td>

                                    <Button component={Link} to={'/keys/delete/' + key.id} variant="contained" color="primary">Delete</Button>
                                    <Button component={Link} to={'/keys/update/' + key.id} variant="contained" color="primary">Update</Button>
                                </tr>
                            </tbody>
                        ))
                        }
                    </table>
                    <div>
                        <Button component={Link} to="/CreateKey" variant="contained" color="primary">Add Key</Button>
                        <Button component={Link} to="/DeleteKey" variant="contained" color="primary">Delete Key</Button>
                    </div>
                </div>
            </main>
        );
    }
}
export default withRouter(Keys);
