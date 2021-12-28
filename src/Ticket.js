import React, { useDebugValue } from 'react';
import './App.css';

function Ticket(props) {
return (
    <div className='ticket'>
        <h3>{props.subject}</h3>
        <h3>{props.description}</h3>
    </div>
);
}

export default Ticket;