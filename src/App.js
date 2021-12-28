import React, {useState} from 'react';
import Ticket from './Ticket';

function App() {
  const [tickets, setTickets] = useState([]);

  const showTickets = () => {
    //make api call here, set the resulting array from server to the 'tickets' state
   setTickets([
    {'subject': 'ticket subject 1', 'description': 'ticket description 1'},
    {'subject': 'ticket subject 2', 'description': 'ticket description 2'},
    {'subject': 'ticket subject 3', 'description': 'ticket description 3'},
  ]) 
  };

  return (
    <div>
      <div><h1><button onClick={showTickets}>Click here to view tickets on your zendesk account</button></h1></div>
      {tickets.map(ticket => (
          <Ticket subject = {ticket.subject} description = {ticket.description}/>
        ))}
    </div>
  );
}  
  
export default App;