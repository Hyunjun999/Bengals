import React from "react";
import { Button } from "react-bootstrap";
import LeftArrowIcon from "../../assets/left-arrow-icon.svg";
import RightArrowIcon from "../../assets/right-arrow-icon.svg";

const InfoTitle = ({ title, activePage, onPageChange }) => (
  <div className="info-title">
    <Button onClick={() => onPageChange(!activePage)} variant="link">
      <img
        className="left-right-svg-icon"
        alt=""
        src={LeftArrowIcon}
        width="30"
        height="30"
      />
    </Button>
    {title}
    <Button onClick={() => onPageChange(!activePage)} variant="link">
      <img
        className="left-right-svg-icon"
        alt=""
        src={RightArrowIcon}
        width="30"
        height="30"
      />
    </Button>
  </div>
);

export default InfoTitle;
