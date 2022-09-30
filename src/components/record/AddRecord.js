import { useState } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import { useEffect } from "react/cjs/react.development";
import recordService from "../../services/record.service";

const AddRecord = () => {

    const [timestamp, setTimestamp] = useState('');
    const [energyConsumption, setEnergyConsumption] = useState('');
    const history = useHistory();
    const { id } = useParams();

    const saveRecord = (e) => {
        e.preventDefault();

        const record = { timestamp, energyConsumption, id };
        if (id) {
            recordService.update(record)
                .then(response => {
                    console.log('Record data updated successfully', response.data);
                    history.push('/record');
                })
                .catch(error => {
                    console.log('Something went wrong', error);
                })
        } else {
            recordService.create(record)
                .then(response => {
                    console.log("Record added successfully", response.data);
                    history.push("/record");
                })
                .catch(error => {
                    console.log('Something went wroing', error);
                })
        }
    }

    useEffect(() => {
        if (id) {
            recordService.get(id)
                .then(record => {
                    setTimestamp(record.data.timestamp);
                    setEnergyConsumption(record.data.energyConsumption);
                })
                .catch(error => {
                    console.log('Something went wrong', error);
                })
        }
    }, [])
    return (
        <div className="container">
            <h3>Add Record</h3>
            <hr />
            <form>
                <div className="form-group">
                    <input
                        type="date"
                        className="form-control col-4"
                        id="timestamp"
                        value={timestamp}
                        onChange={(e) => setTimestamp(e.target.value)}
                        placeholder="Enter Timestamp"
                    />

                </div>
                <div className="form-group">
                    <input
                        type="text"
                        className="form-control col-4"
                        id="energyConsumption"
                        value={energyConsumption}
                        onChange={(e) => setEnergyConsumption(e.target.value)}
                        placeholder="Enter Energy Consumption"
                    />

                </div>
                <div >
                    <button onClick={(e) => saveRecord(e)} className="btn btn-primary">Save</button>
                </div>
            </form>
            <hr />
            <Link to="/record">Back to List</Link>
        </div>
    )
}

export default AddRecord;