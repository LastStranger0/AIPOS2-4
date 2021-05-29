import React, {Component} from "react";
import axios from "axios";
import {Link, withRouter} from "react-router-dom";
import Button from "@material-ui/core/Button";
import {Avatar} from "@material-ui/core";

class Users extends Component{
    constructor(props) {
        super(props);
        this.state = {
            rows: []
        };
    }

    componentDidMount() {
        axios.get('http://localhost:8082/users')
            .then((response) => {this.setState({rows: response.data.data});})
            .catch((error) => {console.log(error); this.setState({ message: error.message })});
    }

    render() {
        return (
            <main role="main" className="container">
                <div align="center">
                    {this.state.rows === null && <p>Loading menu...</p>}
                    <table className="users">
                        <thead>
                            <tr>
                                <th width={50}>id</th>
                                <th width={100}>Date</th>
                                <th width={300}>Email</th>
                                <th width={300}>Password</th>
                                <th width={200}>Username</th>
                                <th width={50}>Avatar</th>
                                <th width={50}>Action</th>
                            </tr>
                        </thead>
                        {this.state.rows && this.state.rows.map(user => (
                            <tbody align="center">
                                <tr>
                                    <td width={50}>{user.id}</td>
                                    <td width={100}>{user.date}</td>
                                    <td width={300}>{user.email}</td>
                                    <td width={300}>{user.password}</td>
                                    <td width={200}>{user.username}</td>
                                    <td width={50}><Avatar alt="Remy Sharp" src={user.linkImage}/></td>
                                    <Button component={Link} to={'/users/delete/' + user.id} variant="contained" color="primary">Delete</Button>
                                    <Button component={Link} to={'/users/update/' + user.id} variant="contained" color="primary">Update</Button>
                                </tr>
                            </tbody>
                        ))
                        }
                    </table>
                    <div>
                        <Button component={Link} to="/CreateUser" variant="contained" color="primary">Add User</Button>
                        <Button component={Link} to="/ImageUser" variant="contained" color="primary">Add Image</Button>
                        <Button component={Link} to="/DeleteUser" variant="contained" color="primary">Delete User</Button>

                    </div>
                </div>
            </main>
        );
    }
}
export default withRouter(Users);
