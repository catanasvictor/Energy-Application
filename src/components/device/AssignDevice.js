import { useState } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import { useEffect } from "react/cjs/react.development";
import deviceService from "../../services/device.service";
import userService from '../../services/user.service';

const AssignDevice = () => {

    const { id } = useParams();
    const [user, setUser] = useState([]);
    const history = useHistory();

    const init = () => {
        userService.getAll()
            .then(response => {
                setUser(response.data);
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })
    }

    useEffect(() => {
        init();
    }, [])

    const associate = (userId) => {
        console.log('Printing id', id);
        deviceService.assoc(userId, id)
            .then(response => {
                console.log('Succesfull association.', response.data);
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })
    }

    return (
        <div className="container">
            <h3>Assign Device to User</h3>
            <hr />
            <form>
                <div>
                    <table className="table table-bordered table-striped">
                        <thead className="thead-dark">
                            <tr>
                                <th>Username</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                user.map(user => (
                                    <tr key={user.id}>
                                        <td>{user.username}</td>
                                        <td>
                                            <button className="btn btn-warning ml-2" onClick={() => {
                                                associate(user.id);
                                                history.push('/device');
                                            }}>Assign</button>
                                        </td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                </div>
            </form>
            <hr />
            <Link to="/device">Back to Devices</Link>
        </div>
    )
}

export default AssignDevice;