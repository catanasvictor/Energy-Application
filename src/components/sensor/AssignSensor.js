import { useState } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import { useEffect } from "react/cjs/react.development";
import deviceService from "../../services/device.service";
import sensorService from '../../services/sensor.service';

const AssignSensor = () => {

    const { id } = useParams();
    const [device, setDevice] = useState([]);
    const history = useHistory();

    const init = () => {
        deviceService.getAll()
            .then(response => {
                setDevice(response.data);
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })
    }

    useEffect(() => {
        init();
    }, [])

    const associate = (deviceId) => {
        console.log('Printing id', id);
        sensorService.assoc(deviceId, id)
            .then(response => {
                console.log('Succesfull association.', response.data);
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })
    }

    return (
        <div className="container">
            <h3>Assign Sensor to Device</h3>
            <hr />
            <form>
                <div>
                    <table className="table table-bordered table-striped">
                        <thead className="thead-dark">
                            <tr>
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                device.map(device => (
                                    <tr key={device.id}>
                                        <td>{device.description}</td>
                                        <td>
                                            <button className="btn btn-warning ml-2" onClick={() => {
                                                associate(device.id);
                                                history.push('/sensor');
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
            <Link to="/device">Back to Sensors</Link>
        </div>
    )
}

export default AssignSensor;