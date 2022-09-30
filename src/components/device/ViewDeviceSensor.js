import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import deviceService from '../../services/device.service';

const ViewDeviceSensor = () => {

    const [sensor, setSensor] = useState([]);
    const { id } = useParams();

    const init = () => {
        deviceService.getDeviceSensor(id)
            .then(response => {
                console.log('Printing devices data', response.data);
                setSensor(response.data);
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })
    }

    useEffect(() => {
        init();

    }, []);

    return (
        <div className="container">
            <h3>Assigned Sensor</h3>
            <hr />
            <div>
                <table className="table table-bordered table-striped">
                    <thead className="thead-dark">
                        <tr>
                            <th>Description</th>
                            <th>Maximum Value</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            sensor.map(sensor => (
                                <tr key={sensor.id}>
                                    <td>{sensor.description}</td>
                                    <td>{sensor.maximumValue}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
                <Link to="/device" className="btn btn-link mb-2">Back to devices</Link>
            </div>
        </div>
    );
}

export default ViewDeviceSensor;