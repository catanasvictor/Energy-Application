import { useState } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import { useEffect } from "react/cjs/react.development";
import deviceService from "../../services/device.service";

const AddDevice = () => {
    const [description, setDescription] = useState('');
    const [location, setLocation] = useState('');
    const [maximumEnergyConsumption, setMaximumEnergyConsumption] = useState('');
    const [baselineEnergyConsumption, setBaselineEnergyConsumption] = useState('');
    const history = useHistory();
    const { id } = useParams();

    const saveDevice = (e) => {
        e.preventDefault();

        const device = { description, location, maximumEnergyConsumption, baselineEnergyConsumption, id };
        if (id) {
            deviceService.update(device)
                .then(response => {
                    console.log('Device data updated successfully', response.data);
                    history.push('/device');
                })
                .catch(error => {
                    console.log('Something went wrong', error);
                })
        } else {
            deviceService.create(device)
                .then(response => {
                    console.log("Device added successfully", response.data);
                    history.push("/device");
                })
                .catch(error => {
                    console.log('Something went wroing', error);
                })
        }
    }

    useEffect(() => {
        if (id) {
            deviceService.get(id)
                .then(device => {
                    setDescription(device.data.description);
                    setLocation(device.data.location);
                    setMaximumEnergyConsumption(device.data.maximumEnergyConsumption);
                    setBaselineEnergyConsumption(device.data.baselineEnergyConsumption);
                })
                .catch(error => {
                    console.log('Something went wrong', error);
                })
        }
    }, [])

    return (
        <div className="container">
            <h3>Add Device</h3>
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
                        id="location"
                        value={location}
                        onChange={(e) => setLocation(e.target.value)}
                        placeholder="Enter Location"
                    />

                </div>
                <div className="form-group">
                    <input
                        type="text"
                        className="form-control col-4"
                        id="maximumEnergyConsumption"
                        value={maximumEnergyConsumption}
                        onChange={(e) => setMaximumEnergyConsumption(e.target.value)}
                        placeholder="Enter Maximum Energy Consumption"
                    />

                </div>
                <div className="form-group">
                    <input
                        type="text"
                        className="form-control col-4"
                        id="baselineEnergyConsumption"
                        value={baselineEnergyConsumption}
                        onChange={(e) => setBaselineEnergyConsumption(e.target.value)}
                        placeholder="Enter Baseline Energy Consumption"
                    />
                </div>
                <div >
                    <button onClick={(e) => saveDevice(e)} className="btn btn-primary">Save</button>
                </div>
            </form>
            <hr />
            <Link to="/device">Back to List</Link>
        </div>
    )
}

export default AddDevice;