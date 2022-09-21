import './App.css';
import axios from "axios";
import React, { useState, useEffect } from "react";
import Accordion from "./components/Accordion";
import { HiPlusCircle, HiSearchCircle, HiUserCircle } from "react-icons/hi";
import { data } from 'autoprefixer';

const User = () => {

  const [data, setData] = useState({})
  const [userId, setUserId] = useState({ id: "" });
  const [loading, setLoading] = useState(false);

  const inputRef = React.createRef()

  const getData = id => {
    const result = axios(
      process.env.REACT_APP_API + '/api/v1/users/' + id,
    ).then((response) => {

      return response.data;
    }).catch((error) => {
      if (error.response.status === 404) {
        console.log("User not found!!!")
      }
    });

    return result;

  }

  const updateInput = () => {
    const inputText = inputRef.current.value;
    console.log("setting the user id: " + inputText);
    setUserId({ id: inputText });
  }

  useEffect(() => {

    const getUser = async () => {

      const userData = await getData(userId.id);

      if (userData) {

        setData(userData);
      }
    }

    if (userId.id !== undefined) {
      getUser();
    }


  }, [userId]);

  let userInfoDisplay;

  userInfoDisplay = userId.id === undefined ?
    <div></div> :
    (<div className='userInfoDisplay pt-10'>
      <div className='flex flex-row space-x-7 pl-3'>
        <div className='text-[#7D7D7D]'>Name</div>
        <div className='text-[#7D7D7D]'>{data.userName === null ? null : data.userName}</div>
      </div>
      <div className='flex flex-row space-x-7 pl-3'>
        <div className='text-[#7D7D7D]'>Surname</div>
        <div className='text-[#7D7D7D]'>{data.surname === null ? null : data.surname}</div>
      </div>


      {data.accounts && <Accordion data={data.accounts} />}
    </div>);

  return (<div className="userInformationSection">
    <div className='inputUserId pt-20'>
      <h1 className='text-2xl font-bold pl-3'>User Information</h1>
      <div className='pt-6 flex flex-row'>
        <div className='relative '>
          <div className='font-semibold pl-3'>User ID</div>
          <input type="number" min="1" ref={inputRef} className=' bg-[#BDB7E7] rounded-full text-white text-s border-transparent' />
        </div>

        <HiSearchCircle onClick={updateInput} className="relative left-5 top-5 w-12 h-12 group-hover:text-white text-[#BDB7E7]" />
      </div>

    </div>
    {userInfoDisplay}
  </div>);

};


function App() {

  const [load, setLoad] = React.useState(false);
  const [users, setUsers] = React.useState([]);
  const [ignored, forceUpdate] = React.useReducer(x => x + 1, 0);



  const userRef = React.createRef()
  const initialAmountRef = React.createRef()
  const createUserNameRef = React.createRef()
  const createUserSurnameRef = React.createRef()
  const newTransactionAccountIdRef = React.createRef()
  const newTransactionOperationRef = React.createRef()
  const newTransactionAmountRef = React.createRef()

  const fetchAllUsers = () => {

    const result = axios(
      process.env.REACT_APP_API + '/api/v1/users/',
    ).then((response) => {

      setUsers(response.data);

    }).catch((error) => {
      if (error.response.status === 404) {
        console.log("User not found!!!")
      }
    })
  }

  useEffect(() => {
    fetchAllUsers();
  }, []);

  const createNewUser = () => {
    const userName = createUserNameRef.current.value;
    const userSurname = createUserSurnameRef.current.value;

    if (!userName || !userName) {

      alert("There is missing information to create an account");
    } else {
      const result = axios.post(process.env.REACT_APP_API + '/api/v1/users', {
        userName: userName,
        surname: userSurname,
      }).then((response) => {
        const user = response.data;
        alert(`The account was created succesfully for the user ${user.userName} ${user.surname}`);

        forceUpdate();
        userRef.current.value = "";
        initialAmountRef.current.value = "";
      }).catch((error) => {

        alert("Problem creating the user");
      });

    }

  }

  const createNewAccount = () => {
    const userId = userRef.current.value;
    const initialAmount = initialAmountRef.current.value;

    setLoad(true);
    if (!userId) {

      alert("There is no userID");
    } else {
      const result = axios.post(process.env.REACT_APP_API + '/api/v1/accounts', {
        amount: initialAmount,
        user: {
          userId: userId
        }
      }).then((response) => {

        alert("The account was created succesfully");

        userRef.current.value = "";
        initialAmountRef.current.value = "";
      }).catch((error) => {
        if (error.response.status === 404) {
          alert("The user does not exists")
        }
      });

      setLoad(false);

    }

  }



  return (
    <div className="App pl-10 pt-7 flex flex-row">
      <div>
        <div className="createAccountSection">
          <h1 className='text-2xl font-bold pl-3'>Create new account</h1>
          <div className='flex flex-row space-x-20 pt-6'>
            <div>
              <div className='font-semibold pl-3'>User Id</div>
              <input ref={userRef} type="text" className='bg-[#BDB7E7] rounded-full text-white text-s border-transparent' />
            </div>
            <div className='relative'>
              <div className='font-semibold pl-3'>Initial amount</div>
              <input ref={initialAmountRef} type="number" className='bg-[#BDB7E7] rounded-full text-white text-s border-transparent' />
            </div>

            <HiPlusCircle disabled={load} onClick={
              createNewAccount} className="relative -left-10 top-5 w-12 h-12 group-hover:text-white text-[#BDB7E7]" />

          </div>
        </div>
        <User></User>
      </div>
      <div className='pl-5'>
        <div className="createNewUserSection">
          <h1 className='text-2xl font-bold pl-3'>Create new user</h1>
          <div className='flex flex-row space-x-20 pt-6'>

            <div className='relative'>
              <div className='font-semibold pl-3'>Name</div>
              <input ref={createUserNameRef} type="text" className='bg-[#BDB7E7] rounded-full text-white text-s border-transparent' />
            </div>
            <div className='relative'>
              <div className='font-semibold pl-3'>Surname</div>
              <input ref={createUserSurnameRef} type="text" className='bg-[#BDB7E7] rounded-full text-white text-s border-transparent' />
            </div>

            <HiUserCircle disabled={load} onClick={
              createNewUser} className="relative -left-10 top-5 w-12 h-12 group-hover:text-white text-[#BDB7E7]" />
          </div>
        </div>
        <div className='pl-3'>
          <h1 className='text-2xl font-bold pt-20'>List of Users</h1>
          <table className="table-fixed border-separate border border-spacing-5 rounded-md bg-[#BDB7E7]">
            <thead>
              <tr>
                <th className='text-white'>ID</th>
                <th className='text-white'>Name</th>
                <th className='text-white'>Surname</th>
              </tr>
            </thead>
            <tbody className='border-spacing-5'>

              {users.map((user) => {
                return (
                  <tr>
                    <td className='text-white'>{user.id}</td>
                    <td className='text-white'>{user.userName}</td>
                    <td className='text-white'>{user.surname}</td>
                  </tr>
                )
              })}

            </tbody>
          </table>
        </div>
      </div>
    </div >
  );
}

export default App;
