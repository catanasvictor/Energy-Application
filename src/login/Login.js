import React from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Container from "@material-ui/core/Container";
import { Grid } from "@material-ui/core";
import axiosInstance from "../axios";

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",

            loginResponse: {
                role: "",
                id: 0
            }
        };
    }

    updateAttributes = (event) => {
        event.preventDefault();
        this.setState({ [event.target.name]: event.target.value, });
    };

    handleInput = event => {
        const { value, name } = event.target;
        this.setState({ [name]: value });
        console.log(value);
    };

    onSubmitFun = event => {
        event.preventDefault();
        let credentilas = { username: this.state.username, password: this.state.password }

        axiosInstance.post("/login", credentilas)
            .then(
                res => {
                    const val = res.data;
                    this.setState({
                        loginResponse: val
                    });
                    console.log("Successful login.");
                    console.log(this.state.loginResponse);

                    sessionStorage.setItem("USER", res.data.role);
                    sessionStorage.setItem("USER_ID", res.data.id);

                    console.log(this.state.loginResponse.role)

                    if (sessionStorage.getItem("USER") === "ADMIN") {
                        this.props.history.push("/admin");
                    }
                    else if (sessionStorage.getItem("USER") === "CLIENT") {
                        this.props.history.push("/client")
                    }
                    else {
                        return <div> Wrong data </div>
                    }
                }
            )
            .catch(error => {
                console.log(error)
            })
    }

    render() {
        return (
            <Container maxWidth="sm">
                <div>
                    <h3>LOGIN</h3>
                    <Grid>
                        <form onSubmit={this.onSubmitFun}>
                            <TextField
                                variant="outlined"
                                margin="normal"
                                required
                                fullWidth
                                id="username"
                                label="Username"
                                name="username"
                                autoComplete="string"
                                onChange={this.handleInput}
                                autoFocus
                            />
                            <TextField
                                variant="outlined"
                                margin="normal"
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                onChange={this.handleInput}
                                autoComplete="current-password"
                            />
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                            >Sign In
                            </Button>
                        </form>
                    </Grid>
                </div>
            </Container>
        );
    }
}

export default Login;