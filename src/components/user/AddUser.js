import { useState } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import { useEffect } from "react/cjs/react.development";
import userService from "../../services/user.service";

const AddUser = () => {
    const [username, setUsername] = useState('');
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [address, setAddress] = useState('');
    const [birthDate, setBirthDate] = useState('');
    const history = useHistory();
    const { id } = useParams();

    const saveUser = (e) => {
        e.preventDefault();

        const user = { username, password, name, address, birthDate, id };
        if (id) {
            //update
            userService.update(user)
                .then(response => {
                    console.log('User data updated successfully', response.data);
                    history.push('/user');
                })
                .catch(error => {
                    console.log('Something went wrong', error);
                })
        } else {
            //create
            userService.create(user)
                .then(response => {
                    console.log("User added successfully", response.data);
                    history.push("/user");
                })
                .catch(error => {
                    console.log('Something went wroing', error);
                })
        }
    };

    const init = () => {
        if (id) {
            userService.get(id)
                .then(user => {
                    setUsername(user.data.username);
                    setPassword(user.data.password);
                    setName(user.data.name);
                    setAddress(user.data.address);
                    setBirthDate(user.data.birthDate);
                })
                .catch(error => {
                    console.log('Something went wrong', error);
                })
        }
    };

    useEffect(() => {
        init();
    }, []);

    if (sessionStorage.getItem("USER") === "ADMIN") {
        return (
            <div className="container">
                <h3>Add User</h3>
                <hr />
                <form>
                    <div className="form-group">
                        <input
                            type="text"
                            className="form-control col-4"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Enter Username"
                        />

                    </div>
                    <div className="form-group">
                        <input
                            type="password"
                            className="form-control col-4"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter Password"
                        />

                    </div>
                    <div className="form-group">
                        <input
                            type="text"
                            className="form-control col-4"
                            id="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            placeholder="Enter Name"
                        />

                    </div>
                    <div className="form-group">
                        <input
                            type="text"
                            className="form-control col-4"
                            id="address"
                            value={address}
                            onChange={(e) => setAddress(e.target.value)}
                            placeholder="Enter Address"
                        />
                    </div>
                    <div className="form-group">
                        <input
                            type="date"
                            className="form-control col-4"
                            id="birthDate"
                            value={birthDate}
                            onChange={(e) => setBirthDate(e.target.value)}
                            placeholder="Enter Birthdate"
                        />
                    </div>
                    <div >
                        <button onClick={(e) => saveUser(e)} className="btn btn-primary">Save</button>
                    </div>
                </form>
                <hr />
                <Link to="/user">Back to List</Link>
            </div>
        );
    }
    else {
        return (<div>You are not logged in as an ADMIN.</div>);
    }
};

export default AddUser;