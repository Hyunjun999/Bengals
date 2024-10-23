import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Button } from "react-bootstrap";

import arrowCircleIcon from "./assets/arrowCircleIcon.svg";
import circleIcon from "./assets/circleIcon.svg";
import Sidebar from "./Components/Sidebar";
import USMap from "./Components/USMap";
import Brand from "./Components/Brand";
import ToAnalysisButtons from "./Components/ToAnalysisButtons";

function Home() {
  const [selectedState, setSelectedState] = useState("SELECT A STATE");
  const [hoveredLocation, setHoveredLocation] = useState(null);
  const [showButtons, setShowButtons] = useState(false);
  const [showSideBar, setShowSideBar] = useState(false);
  const customStates = ["Alabama", "Mississippi", "Pennsylvania"];
  const navigate = useNavigate();
  const toEnsemble = (state, option) => {
    navigate(`/Ensemble/${state}`, { state: { selectedState, option } });
  };
  const toRandom = (state, option) => {
    navigate(`/Random/${state}`, { state: { selectedState, option } });
  };
  return (
    <>
      <div className="body">
        <Sidebar show={showSideBar} handleClose={() => setShowSideBar(false)} />
        <div className="body_home">
          <Brand />
          <Container>
            <div className="text_question">IS A FAIR VOTE BEING HELD?</div>
            <div className="text_selectedState">
              {hoveredLocation
                ? hoveredLocation.toUpperCase()
                : selectedState.toUpperCase()}
            </div>
            <Container className="map_us">
              <USMap
                hoveredLocation={hoveredLocation}
                selectedState={selectedState}
                setHoveredLocation={setHoveredLocation}
                setSelectedState={setSelectedState}
              />
            </Container>
            {customStates.includes(selectedState) && (
              <div className="button_toAnalysis">
                <Button variant="link" onClick={setShowButtons}>
                  <img
                    alt=""
                    src={arrowCircleIcon}
                    width="45px"
                    height="45px"
                    className="svgIcon"
                  />
                </Button>
              </div>
            )}
            <div className="dataExplaination">
              <span className="text_Available_State">
                {" "}
                <img
                  alt=""
                  src={circleIcon}
                  width="10px"
                  height="10px"
                  className="svgIcon"
                />
                &nbsp;Available State
              </span>
            </div>
          </Container>
          <ToAnalysisButtons
            show={showButtons}
            handleClose={() => setShowButtons(false)}
            toEnsemble={toEnsemble}
            toRandom={toRandom}
            selectedState={selectedState}
          />
        </div>
      </div>
    </>
  );
}

export default Home;
