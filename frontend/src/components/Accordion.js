import React, { useState } from "react";
import AccordionItem from "./AccordionItem";

const Accordion = ({ data }) => {
    const [Index, setIndex] = useState(false);

    return (

        data.map((data, index) => {
            return (
                <AccordionItem
                    accountNumber={data.accountId}
                    accountBalance={data.amount}
                    transactions={data.transactions}
                    Index={Index}
                    Id={index}
                    setIndex={setIndex}
                ></AccordionItem>
            );
        })

    );
};
export default Accordion;