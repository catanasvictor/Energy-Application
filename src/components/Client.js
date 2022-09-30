import { Link } from 'react-router-dom';

const Client = () => {

    const user_id = sessionStorage.getItem("USER_ID");

    if (sessionStorage.getItem("USER") === "CLIENT") {
        return (
            <div className="container">
                <h3>Welcome Customer!</h3>
                <hr />
                <div>
                    <Link to="/device" className="btn btn-primary mb-2">Manage Devices</Link>

                    <Link to="/sensor" className="btn btn-primary mb-2">Manage Sensors</Link>

                    <Link to={`/record/user/${user_id}`} className="btn btn-primary mb-2">Manage Records</Link>
                    
                    <Link to="/chart" className="btn btn-primary mb-2">View Chart</Link>
                    <hr />
                    <Link to="/login" className="btn btn-link mb-2">Logout</Link>
                </div>
            </div>
        );
    }
    else {
        return (<div>You are not logged in as a CLIENT.</div>);
    }
}

export default Client;