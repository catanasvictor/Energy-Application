import { useState } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import { useEffect } from "react/cjs/react.development";
import sensorService from "../../services/sensor.service";

const AddSensor = () => {

    const [description, setDescription] = useState('');
    const [maximumValue, setMaximumValue] = useState('');
    const history = useHistory();
    const { id } = useParams();

    const saveSensor = (e) => {
        e.preventDefault();

        const sensor = { description, maximumValue, id };
        if (id) {
            sensorService.update(sensor)
                .then(response => {
                    console.log('Sensor data updated successfully', response.data);
                    history.push('/sensor');
                })
                .catch(error => {
                    console.log('Something went wrong', error);
                })
        } else {
            sensorService.create(sensor)
                .then(response => {
                    console.log("Sensor added successfully", response.data);
                    history.push("/sensor");
                })
                .catch(error => {
                    console.log('Something went wroing', error);
                })
        }
    }

    useEffect(() => {
        if (id) {
            sensorService.get(id)
                .then(sensor => {
                    setDescription(sensor.data.description);
                    setMaximumValue(sensor.data.maximumValue);
                })
                .catch(error => {
                    console.log('Something went wrong', error);
                })
        }
    }, [])
    return (
        <div className="container">
            <h3>Add Sensor</h3>
            <hr />
            <form>
                <div className="form-group">
                    <input
                        type="text"
                        className="form-control col-4"
                        id="description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        placeholder="Enter Description"
                    />

                </div>
                <div className="form-group">
                    <input
                        type="text"
                        className="form-control col-4"
                        id="maximumValue"
                        value={maximumValue}
                        onChange={(e) => setMaximumValue(e.target.value)}
                        placeholder="Enter Maximum Value"
                    />
                </div>
                <div >
                    <button onClick={(e) => saveSensor(e)} className="btn btn-primary">Save</button>
                </div>
            </form>
            <hr />
            <Link to="/sensor">Back to List</Link>
        </div>
    )
}

export default AddSensor;