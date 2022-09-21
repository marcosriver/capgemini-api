import { Accordion } from "flowbite-react";
import React from "react";
import { HiArrowCircleDown, HiX } from "react-icons/hi";

const AccordionItem = ({ accountNumber, accountBalance, transactions, Id, Index, setIndex }) => {
    const handleSetIndex = (Id) => Index !== Id ? setIndex(Id) : setIndex(undefined);
    const closeIndex = () => setIndex(undefined)

    return (
        <>
            <div
                onClick={() => handleSetIndex(Id)}
                className={`flex group cursor-pointer w-full h-16 justify-between  items-center p-2 mt-2
                 ${Index === Id ? "rounded-t-md hover:bg-white bg-[#BDB7E7] hover:shadow-lg" : "rounded-md bg-white hover:bg-[#BDB7E7]"}
                     focus:bg-pink-500 `}
            >
                <div className="flex group cursor-pointer">
                    <div className={`font-semibold ${Index === Id ? "text-white group-hover:text-[#BDB7E7]" : "text-[#BDB7E7] group-hover:text-white"} `}>
                        {`Account ${accountNumber} - Balance ${accountBalance}`}
                    </div>
                </div>
                <div className="flex items-center justify-center pr-10">
                    {Index !== Id ? (
                        <HiArrowCircleDown className="w-6 h-6 group-hover:text-white text-[#BDB7E7]" />
                    ) : (
                        <HiX onClick={closeIndex} className={`${Index === Id ? "group-hover:text-[#BDB7E7] text-white" : "group-hover:text-white text-white"} w-6 h-6 `} />
                    )}
                </div>
            </div>

            {Index === Id && (
                <div className="bg-pink-100  font-semibold text-pink-500 w-full h-auto  rounded-b-md p-4 border-l-2 border-blue-300 mb-2 ">
                    <ol>
                        {transactions.length > 0 ? transactions.map((transaction, index) => {
                            return (
                                <li>Transaction {transaction.transactionId} - {transaction.transactionType} - {transaction.amount}</li>)
                        }) :
                            <li>There are no transactions</li>}
                    </ol>
                </div>
            )}
        </>
    );
};

export default AccordionItem;