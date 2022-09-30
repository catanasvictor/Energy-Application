import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import userService from '../../services/user.service';

const ViewUserDevices = () => {

    const [device, setDevice] = useState([]);

    const { id } = useParams();

    const init = () => {
        userService.getUserDevices(id)
            .then(response => {
                console.log('Printing devices data', response.data);
                setDevice(response.data);
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })
    }

    useEffect(() => {
        init();
    }, []);

    if (sessionStorage.getItem("USER") === "ADMIN") {
        return (
            <div className="container">
                <h3>List of Devices</h3>
                <hr />
                <div>
                    <table className="table table-bordered table-striped">
                        <thead className="thead-dark">
                            <tr>
                                <th>Description</th>
                                <th>Location</th>
                                <th>Maximum Energy Consumption</th>
                                <th>Baseline Energy Consumption</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                device.map(device => (
                                    <tr key={device.id}>
                                        <td>{device.description}</td>
                                        <td>{device.location}</td>
                                        <td>{device.maximumEnergyConsumption}</td>
                                        <td>{device.baselineEnergyConsumption}</td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                    <Link to="/user" className="btn btn-link mb-2">Back to users</Link>
                </div>
            </div>
        );
    }
    else {
        return (<div>You are not logged in as an ADMIN.</div>);
    }
};

export default ViewUserDevices;