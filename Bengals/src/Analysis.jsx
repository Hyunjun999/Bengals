import React, { useState, useEffect, useRef, useMemo } from "react";
import { useParams } from "react-router-dom";
import bengalLogo from "./assets/Bengal.svg";
import usaMapData from "@svg-maps/usa";
import congDist from "./assets/ms_cvap_2020_cd.json";
import copyGeo from "./assets/copyGeo.json";
import { MapContainer, GeoJSON } from "react-leaflet";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
  ZAxis,
  Scatter,
  ComposedChart,
  LineChart,
  Line,
  PieChart,
  Pie,
  Sector,
} from "recharts";
import {
  Offcanvas,
  Nav,
  Navbar,
  NavDropdown,
  Container,
  Button,
  Alert,
  Table,
  Form,
  Row,
  Col,
} from "react-bootstrap";
const boxPlots1 = [
  {
    name: "District 1",
    min: 0.05,
    lowerQuartile: 0.1,
    median: 0.15,
    upperQuartile: 0.2,
    max: 0.25,
    average: 0.18,
  },
  {
    name: "District 2",
    min: 0.12,
    lowerQuartile: 0.16,
    median: 0.22,
    upperQuartile: 0.25,
    max: 0.3,
    average: 0.24,
  },
  {
    name: "District 3",
    min: 0.3,
    lowerQuartile: 0.35,
    median: 0.4,
    upperQuartile: 0.45,
    max: 0.5,
    average: 0.42,
  },
  {
    name: "District 4",
    min: 0.38,
    lowerQuartile: 0.42,
    median: 0.5,
    upperQuartile: 0.55,
    max: 0.6,
    average: 0.45,
  },
];
const boxPlots2 = [
  {
    name: "District 1",
    min: 0.38,
    lowerQuartile: 0.42,
    median: 0.5,
    upperQuartile: 0.55,
    max: 0.6,
    average: 0.45,
  },
];

const data_curve1 = [
  {
    Republicans: 0,
    Democrats: 0,
  },
  {
    Republicans: 0.5,
    Democrats: 0.4,
  },
  {
    Republicans: 0.5,
    Democrats: 0.4,
  },
  {
    Republicans: 1,
    Democrats: 1,
  },
];

// Horizontal Line
const HorizonBar = (props) => {
  const { x, y, width, height } = props;

  if (x == null || y == null || width == null || height == null) {
    return null;
  }

  return (
    <line x1={x} y1={y} x2={x + width} y2={y} stroke={"#000"} strokeWidth={3} />
  );
};

// Whisker
const DotBar = (props) => {
  const { x, y, width, height } = props;

  if (x == null || y == null || width == null || height == null) {
    return null;
  }

  return (
    <line
      x1={x + width / 2}
      y1={y + height}
      x2={x + width / 2}
      y2={y}
      stroke={"#000"}
      strokeWidth={5}
      strokeDasharray={"5"}
    />
  );
};

// BoxPlot
const useBoxPlot = (boxPlots) => {
  const data = useMemo(
    () =>
      boxPlots.map((v) => {
        return {
          name: v.name,
          min: v.min,
          bottomWhisker: v.lowerQuartile - v.min,
          bottomBox: v.median - v.lowerQuartile,
          topBox: v.upperQuartile - v.median,
          topWhisker: v.max - v.upperQuartile,
          average: v.average,
          size: 250,
        };
      }),
    [boxPlots]
  );

  return data;
};

function Analysis() {
  const [onMMD, setOnMMD] = useState(false);
  const { id: selectedState } = useParams();
  const [activeIndex, setActiveIndex] = useState(0);
  const onPieEnter = (_, index) => {
    setActiveIndex(index);
  };
  //   const [selectedState, setSelectedState] = useState("Mississipi");
  // const [selectedDistrictPop_SMD, setselectedDistrictPop_SMD] = useState([
  //   congDist.features[0]["properties"]["vap"],
  //   congDist.features[0]["properties"]["vap_white"],
  //   congDist.features[0]["properties"]["vap_asian"],
  //   congDist.features[0]["properties"]["vap_black"],
  //   congDist.features[0]["properties"]["vap_hisp"],
  //   0,
  //   0,
  // ]); // [population, White, Asian, Black, Hispanic, Democratic, Republican]
  // const [selectedDistrictPop_MMD, setselectedDistrictPop_MMD] = useState([
  //   copyGeo.features[0]["properties"]["vap"],
  //   copyGeo.features[0]["properties"]["vap_white"],
  //   copyGeo.features[0]["properties"]["vap_asian"],
  //   copyGeo.features[0]["properties"]["vap_black"],
  //   copyGeo.features[0]["properties"]["vap_hisp"],
  //   0,
  //   0,
  // ]);
  const [onPieChart, setOnPieChart] = useState(false);
  const renderActiveShape = (props) => {
    const RADIAN = Math.PI / 180;
    const {
      cx,
      cy,
      midAngle,
      innerRadius,
      outerRadius,
      startAngle,
      endAngle,
      fill,
      payload,
      percent,
      value,
    } = props;
    const sin = Math.sin(-RADIAN * midAngle);
    const cos = Math.cos(-RADIAN * midAngle);
    const sx = cx + (outerRadius + 10) * cos;
    const sy = cy + (outerRadius + 10) * sin;
    const mx = cx + (outerRadius + 30) * cos;
    const my = cy + (outerRadius + 30) * sin;
    const ex = mx + (cos >= 0 ? 1 : -1) * 22;
    const ey = my;
    const textAnchor = cos >= 0 ? "start" : "end";

    return (
      <g>
        <text x={cx} y={cy} dy={8} textAnchor="middle" fill={fill}>
          {payload.name}
        </text>
        <Sector
          cx={cx}
          cy={cy}
          innerRadius={innerRadius}
          outerRadius={outerRadius}
          startAngle={startAngle}
          endAngle={endAngle}
          fill={fill}
        />
        <Sector
          cx={cx}
          cy={cy}
          startAngle={startAngle}
          endAngle={endAngle}
          innerRadius={outerRadius + 6}
          outerRadius={outerRadius + 10}
          fill={fill}
        />
        <path
          d={`M${sx},${sy}L${mx},${my}L${ex},${ey}`}
          stroke={fill}
          fill="none"
        />
        <circle cx={ex} cy={ey} r={2} fill={fill} stroke="none" />
        <text
          x={ex + (cos >= 0 ? 1 : -1) * 12}
          y={ey}
          textAnchor={textAnchor}
          fill="#333"
        >{`${(percent * 100).toFixed(2)}%`}</text>
        <text
          x={ex + (cos >= 0 ? 1 : -1) * 12}
          y={ey}
          dy={18}
          textAnchor={textAnchor}
          fill="#999"
        >
          {`(${value})`}
        </text>
      </g>
    );
  };
  const [data_barchart_SMD, setData_barchart_SMD] = useState([]);
  const [data_barchart_MMD, setData_barchart_MMD] = useState([]);
  const [data_barchart_SMD2, setData_barchart_SMD2] = useState([]);
  const [data_barchart_MMD2, setData_barchart_MMD2] = useState([]);
  // const [selectedDistrictSMD, setSelectedDistrictSMD] = useState(null);
  // const [selectedDistrictMMD, setSelectedDistrictMMD] = useState(null);
  const [hoveredLocation, setHoveredLocation] = useState(null);
  const customStates = ["Alabama", "Mississippi", "Pennsylvania"];
  const [showBelowStateSelection, setShowBelowStateSelection] = useState(true);
  const [showInfo1, setShowInfo1] = useState(false);
  const [showInfo2, setShowInfo2] = useState(false);
  const [showGraph, setShowGraph] = useState("A");
  const [coordinate, setCoordinate] = useState([0, 0]);
  const data_boxPlot = [useBoxPlot(boxPlots1), useBoxPlot(boxPlots2)];
  const formatXAxisTick = (tick) => {
    return `${(tick * 100).toFixed(0)}%`;
  };
  const formatYAxisTick = (tick) => {
    return `${(tick * 100).toFixed(0)}%`;
  };
  const stateSelectionRef = useRef(0);
  const minorityFairnessRef = useRef(0);
  const politicalFairnessRef = useRef(0);
  const analysisRef = useRef(0);
  //   const mapHandler = (value) => {
  //     setSelectedState(value);
  //     if (value == "Alabama") {
  //       setCoordinate([32.8067, -86.7911]);
  //     } else if (value == "Mississippi") {
  //       setCoordinate([32.3547, -90.0]);
  //     } else {
  //       setCoordinate([40.8781, -77.7996]);
  //     }
  //     analysisRef.current.scrollIntoView();
  //   };

  const onEachDistrict_SMD = (district, layer, index) => {
    const onMouseOver = (e) => {
      layer.setStyle({
        fillColor: "rgb(40, 38, 38)",
      });
    };

    const onMouseOut = (e) => {
      layer.setStyle({
        fillColor: "rgb(220, 25, 10)",
      });
    };

    // const onClick = (e) => {
    //   setSelectedDistrictSMD(district.properties);
    //   setselectedDistrictPop_SMD([
    //     district.properties.vap,
    //     district.properties.vap_white,
    //     district.properties.vap_asian,
    //     district.properties.vap_black,
    //     district.properties.vap_hisp,
    //     0,
    //     0,
    //   ]);
    //   // layer.setStyle({
    //   //   weight: 3,
    //   //   fillColor: "blue",
    //   // });
    // };
    const onAdd = (e) => {
      const label = L.divIcon({
        className: "district-label",
        html: `<div style="font-size: 20px; color: black;">${index + 1}</div>`,
      });
      L.marker(layer.getBounds().getCenter(), { icon: label }).addTo(
        layer._map
      );
    };
    layer.setStyle({
      color: "rgba(241, 243, 243, 1)",
      fillColor: "rgb(220, 25, 10)",
    });
    layer.on({
      mouseout: onMouseOut,
      mouseover: onMouseOver,
      // click: onClick,
      add: onAdd,
    });
  };
  const onEachDistrict_MMD = (district, layer, index) => {
    const onMouseOver = (e) => {
      layer.setStyle({
        weight: 4,
        fillColor: "rgb(40, 38, 38)",
      });
    };

    const onMouseOut = (e) => {
      layer.setStyle({
        weight: 3,
        fillColor: "rgb(220, 25, 10)",
      });
    };

    const onClick = (e) => {
      setSelectedDistrictMMD(district.properties);
      setselectedDistrictPop_MMD([
        district.properties.vap,
        district.properties.vap_white,
        district.properties.vap_asian,
        district.properties.vap_black,
        district.properties.vap_hisp,
        0,
        0,
      ]);
    };
    const onAdd = (e) => {
      const label = L.divIcon({
        className: "district-label",
        html: `<div style="font-size: 20px; color: black;">${index + 1}</div>`,
      });
      L.marker(layer.getBounds().getCenter(), { icon: label }).addTo(
        layer._map
      );
    };
    layer.setStyle({
      color: "rgba(241, 243, 243, 1)",
      fillColor: "rgb(220, 25, 10)",
    });
    layer.on({
      mouseout: onMouseOut,
      mouseover: onMouseOver,
      click: onClick,
      add: onAdd,
    });
  };
  useEffect(() => {
    const selectedDistrictSMD = congDist.features;
    const selectedDistrictMMD = copyGeo.features;
    let data_barchart_SMD = [];
    let data_barchart_MMD = [];
    let data_barchart_SMD2 = [];
    let data_barchart_MMD2 = [];
    if (selectedState === "Alabama") {
      setCoordinate([32.8067, -86.7911]);
    } else if (selectedState === "Mississippi") {
      setCoordinate([32.3547, -90.0]);
    } else {
      setCoordinate([40.8781, -77.7996]);
    }
    for (var i = 0; i < selectedDistrictSMD.length; i++) {
      data_barchart_SMD.push({
        name: i + 1,
        White: selectedDistrictSMD[i]["properties"]["vap_white"],
        Aisan: selectedDistrictSMD[i]["properties"]["vap_asian"],
        Black: selectedDistrictSMD[i]["properties"]["vap_black"],
        Hispanic: selectedDistrictSMD[i]["properties"]["vap_hisp"],
      });
      data_barchart_SMD2.push({
        name: i + 1,
        Democrats: 90000,
        Republicans: 50000,
      });
    }
    setData_barchart_SMD(data_barchart_SMD);
    setData_barchart_SMD2(data_barchart_SMD2);
    console.log(data_barchart_SMD2);
    for (var i = 0; i < selectedDistrictMMD.length; i++) {
      data_barchart_MMD.push({
        name: i + 1,
        White: selectedDistrictMMD[i]["properties"]["vap_white"],
        Aisan: selectedDistrictMMD[i]["properties"]["vap_asian"],
        Black: selectedDistrictMMD[i]["properties"]["vap_black"],
        Hispanic: selectedDistrictMMD[i]["properties"]["vap_hisp"],
      });
      data_barchart_MMD2.push({
        name: i + 1,
        Democrats: 50000,
        Republicans: 50000,
      });
    }
    setData_barchart_MMD(data_barchart_MMD);
    setData_barchart_MMD2(data_barchart_MMD2);
  }, [selectedState]);

  return (
    <>
      <div className="body">
        <Navbar
          expand={false}
          sticky="top"
          data-bs-theme="dark"
          className="sidebar"
        >
          <Navbar.Toggle
            className="sidebar_button"
            aria-controls="offcanvasNavbar"
          >
            {" "}
            <svg
              version="1.0"
              xmlns="http://www.w3.org/2000/svg"
              width="40px"
              height="40px"
              viewBox="0 0 1280.000000 1280.000000"
              transform="rotate(180)"
              preserveAspectRatio="xMidYMid meet"
            >
              <metadata>
                Created by potrace 1.15, written by Peter Selinger 2001-2017
              </metadata>
              <g
                transform="translate(0.000000,1280.000000) scale(0.100000,-0.100000)"
                fill="rgb(40, 38, 38)"
                stroke="rgba(255, 255, 255, 1)"
                strokeWidth="400"
              >
                <path
                  d="M1000 10050 l0 -1510 4643 0 4642 0 755 755 755 755 -755 755 -755
755 -4642 0 -4643 0 0 -1510z"
                />
                <path
                  d="M1000 6390 l0 -1510 4648 0 4647 0 753 753 752 752 -758 758 -757
757 -4643 0 -4642 0 0 -1510z"
                />
                <path
                  d="M1000 2750 l0 -1510 4643 0 4642 0 753 753 c413 413 752 757 752 762
0 5 -336 346 -747 757 l-748 748 -4647 0 -4648 0 0 -1510z"
                />
              </g>
            </svg>
          </Navbar.Toggle>
          <Navbar.Offcanvas
            aria-labelledby="offcanvasNavbarLabel"
            className="sidebar_offcanvas"
            placement="end"
          >
            <Offcanvas.Header closeButton></Offcanvas.Header>
            <Offcanvas.Body className="sidebar_body">
              <h1 id="textring">
                <span className="char1">F</span>
                <span className="char2">A</span>
                <span className="char3">I</span>
                <span className="char4">R</span>
                <span className="char5">V</span>
                <span className="char6">O</span>
                <span className="char7">T</span>
                <span className="char8">E</span>
                <span className="char9">*</span>
                <span className="char10">B</span>
                <span className="char11">E</span>
                <span className="char12">N</span>
                <span className="char13">G</span>
                <span className="char14">A</span>
                <span className="char15">L</span>
                <span className="char16">*</span>
              </h1>
              <Nav className="sidebar_nav">
                <Nav.Link href="/">STATE SELECTION</Nav.Link>
                {/* <NavDropdown title="ANALYSIS" className="sidebar_dropdown">
                  <NavDropdown.Item
                    className="sidebar_dropdownItem"
                    onClick={() => {
                      analysisRef.current.scrollIntoView();
                      minorityFairnessRef.current?.click();
                    }}
                  >
                    Minority Fairness
                  </NavDropdown.Item>
                  <NavDropdown.Item
                    className="sidebar_dropdownItem"
                    onClick={() => {
                      analysisRef.current.scrollIntoView();
                      politicalFairnessRef.current?.click();
                    }}
                  >
                    Political Fairness
                  </NavDropdown.Item>
                </NavDropdown> */}
                <Nav.Link href="./about">ABOUT</Nav.Link>
              </Nav>
            </Offcanvas.Body>
          </Navbar.Offcanvas>
        </Navbar>
        <Navbar data-bs-theme="dark" className="brand">
          <Navbar.Brand href="/" className="text_FAIRWIN">
            <img
              alt=""
              src={bengalLogo}
              width="40"
              height="40"
              className="bengal"
            />
            &nbsp; FAIRWIN
          </Navbar.Brand>
        </Navbar>
        <div className="body2" ref={analysisRef}>
          <Container>
            <Row>
              <Col className="col_stateInformation">
                <Row>
                  <h1 className="text_selectedState_Analysis">
                    {selectedState.toUpperCase()}
                  </h1>
                </Row>
                <Row>
                  <Table striped bordered hover>
                    <thead>
                      <tr>
                        <td className="table_0">Population</td>
                        <td></td>
                        <td className="table_0">Voting Population</td>
                        <td></td>
                      </tr>
                      <tr>
                        <td className="table_0">Representative Seats</td>
                        <td></td>
                        <td className="table_0">Representative Party</td>
                        <td></td>
                      </tr>
                    </thead>
                  </Table>
                </Row>
                <Row>
                  <div className="districtMap">
                    {!onMMD && (
                      <div className="mapContainer">
                        <MapContainer
                          key={coordinate}
                          center={coordinate}
                          zoom={6.5}
                          zoomControl={false}
                          scrollWheelZoom={false}
                          className="map_district"
                        >
                          <GeoJSON
                            data={congDist.features}
                            onEachFeature={(district, layer) => {
                              onEachDistrict_SMD(
                                district,
                                layer,
                                congDist.features.indexOf(district)
                              );
                            }}
                          />
                        </MapContainer>
                      </div>
                    )}

                    {/* </MapContainer> */}

                    {/* </Container> */}
                    {/* </td>
                      <td> */}
                    {/* <Container> */}
                    {onMMD && (
                      <Container>
                        <MapContainer
                          key={coordinate}
                          center={coordinate}
                          zoom={6.5}
                          zoomControl={false}
                          scrollWheelZoom={false}
                          className="map_district"
                        >
                          <GeoJSON
                            data={copyGeo.features}
                            onEachFeature={(district, layer) =>
                              onEachDistrict_MMD(
                                district,
                                layer,
                                copyGeo.features.indexOf(district)
                              )
                            }
                          />
                        </MapContainer>
                      </Container>
                    )}
                    <Form>
                      <Form.Check
                        type="switch"
                        id="custom-switch"
                        onChange={() => setOnMMD(!onMMD)}
                      />
                    </Form>
                  </div>
                </Row>
              </Col>
              <Col className="col_districtInformation">
                <Row>
                  <Nav
                    variant="tabs"
                    defaultActiveKey="link-1"
                    className="navbar_fairness"
                  >
                    <Nav.Item>
                      <Nav.Link
                        eventKey="link-1"
                        className="text_navElement_fairness"
                        onClick={() => setShowGraph("A")}
                        ref={minorityFairnessRef}
                      >
                        Racial Population
                      </Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                      <Nav.Link
                        eventKey="link-2"
                        className="text_navElement_fairness"
                        onClick={() => setShowGraph("B")}
                        ref={politicalFairnessRef}
                      >
                        Box & Whisker
                      </Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                      <Nav.Link
                        eventKey="link-3"
                        className="text_navElement_fairness"
                        onClick={() => setShowGraph("C")}
                        ref={minorityFairnessRef}
                      >
                        Political Party
                      </Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                      <Nav.Link
                        eventKey="link-4"
                        className="text_navElement_fairness"
                        onClick={() => setShowGraph("D")}
                        ref={minorityFairnessRef}
                      >
                        Curve
                      </Nav.Link>
                    </Nav.Item>
                  </Nav>
                </Row>
                {showGraph == "A" && (
                  <div className="analysis1">
                    {/* <h2 className="text_subQuestion1_1">
                WILL FAIR REPRESENTATION ACT(FRA) for MMD
                <span className="text_subQuestion2">
                  {" "}
                  INCREASE MINORITY FAIRNESS?
                </span>
                <Button
                  variant="link"
                  className="button_information"
                  onClick={() => setShowInfo1(true)}
                >
                  <svg
                    fill="rgb(40, 38, 38)"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    xmlnsXlink="http://www.w3.org/1999/xlink"
                    width="30px"
                    height="30px"
                    viewBox="0 0 416.979 416.979"
                    xmlSpace="preserve"
                  >
                    <g>
                      <path
                        d="M356.004,61.156c-81.37-81.47-213.377-81.551-294.848-0.182c-81.47,81.371-81.552,213.379-0.181,294.85
    c81.369,81.47,213.378,81.551,294.849,0.181C437.293,274.636,437.375,142.626,356.004,61.156z M237.6,340.786
    c0,3.217-2.607,5.822-5.822,5.822h-46.576c-3.215,0-5.822-2.605-5.822-5.822V167.885c0-3.217,2.607-5.822,5.822-5.822h46.576
    c3.215,0,5.822,2.604,5.822,5.822V340.786z M208.49,137.901c-18.618,0-33.766-15.146-33.766-33.765
    c0-18.617,15.147-33.766,33.766-33.766c18.619,0,33.766,15.148,33.766,33.766C242.256,122.755,227.107,137.901,208.49,137.901z"
                      />
                    </g>
                  </svg>
                </Button>
              </h2> */}
                    {showInfo1 && (
                      <Alert
                        variant="dark"
                        className="alert_dataInformation"
                        onClose={() => setShowInfo1(false)}
                        dismissible
                      >
                        <Alert.Heading>ABOUT THE DATA</Alert.Heading>
                        <p>In this section, ..</p>
                      </Alert>
                    )}
                    <div className="tableContainer_analysis">
                      <Row style={{ padding: 0 }}>
                        <div
                          className="graphContainer"
                          style={{ width: "100%", height: 300 }}
                        >
                          <ResponsiveContainer
                            className="responsiveContainer"
                            width="100%"
                            height="100%"
                          >
                            <BarChart
                              width={500}
                              height={300}
                              data={data_barchart_SMD}
                              margin={{
                                top: 20,
                                right: 30,
                                left: 20,
                                bottom: 5,
                              }}
                            >
                              <CartesianGrid strokeDasharray="3 3" />
                              <XAxis dataKey="name" />
                              <YAxis />
                              <Tooltip />
                              <Legend />
                              <Bar dataKey="White" fill="#ffc658" />
                              <Bar dataKey="Asian" stackId="a" fill="#8884d8" />
                              <Bar dataKey="Black" stackId="a" fill="#82ca9d" />
                              <Bar
                                dataKey="Hispanic"
                                stackId="a"
                                fill="#f7a1b8"
                              />
                            </BarChart>
                          </ResponsiveContainer>
                        </div>
                      </Row>
                      <Row>
                        <div style={{ width: "100%", height: 300 }}>
                          <ResponsiveContainer width="100%" height="100%">
                            <BarChart
                              width={500}
                              height={300}
                              data={
                                data_barchart_MMD
                                // {
                                //   name: "White",
                                //   White: selectedDistrictPop_MMD[1],
                                // },
                                // {
                                //   name: "Non-White",
                                //   Aisan: selectedDistrictPop_MMD[2],
                                //   Black: selectedDistrictPop_MMD[3],
                                //   Hispanic: selectedDistrictPop_MMD[4],
                                // },
                              }
                              margin={{
                                top: 20,
                                right: 30,
                                left: 20,
                                bottom: 5,
                              }}
                            >
                              <CartesianGrid strokeDasharray="3 3" />
                              <XAxis dataKey="name" />
                              <YAxis />
                              <Tooltip />
                              <Legend />
                              <Bar dataKey="White" fill="#ffc658" />
                              <Bar dataKey="Asian" stackId="a" fill="#8884d8" />
                              <Bar dataKey="Black" stackId="a" fill="#82ca9d" />
                              <Bar
                                dataKey="Hispanic"
                                stackId="a"
                                fill="#f7a1b8"
                              />
                            </BarChart>
                          </ResponsiveContainer>
                        </div>
                      </Row>
                      {/* <Row>
                        <Col>
                          <div style={{ width: "100%", height: 300 }}>
                            <ResponsiveContainer width="100%" height="100%">
                              <PieChart width={500} height={500}>
                                <Pie
                                  activeIndex={activeIndex}
                                  activeShape={renderActiveShape}
                                  data={[
                                    {
                                      name: "White",
                                      value: selectedDistrictPop_SMD[1],
                                    },
                                    {
                                      name: "Asian",
                                      value: selectedDistrictPop_SMD[2],
                                    },
                                    {
                                      name: "Black",
                                      value: selectedDistrictPop_SMD[3],
                                    },
                                    {
                                      name: "Hispanic",
                                      value: selectedDistrictPop_SMD[4],
                                    },
                                  ].sort((a, b) => b.value - a.value)}
                                  cx="50%"
                                  cy="50%"
                                  innerRadius={60}
                                  outerRadius={80}
                                  fill="#4b8fe2"
                                  dataKey="value"
                                  onMouseEnter={onPieEnter}
                                />
                              </PieChart>
                            </ResponsiveContainer>
                          </div>
                        </Col>
                        <Col>
                          <div style={{ width: "100%", height: 300 }}>
                            <ResponsiveContainer width="100%" height="100%">
                              <PieChart width={500} height={500}>
                                <Pie
                                  activeIndex={activeIndex}
                                  activeShape={renderActiveShape}
                                  data={[
                                    {
                                      name: "White",
                                      value: selectedDistrictPop_MMD[1],
                                    },
                                    {
                                      name: "Asian",
                                      value: selectedDistrictPop_MMD[2],
                                    },
                                    {
                                      name: "Black",
                                      value: selectedDistrictPop_MMD[3],
                                    },
                                    {
                                      name: "Hispanic",
                                      value: selectedDistrictPop_MMD[4],
                                    },
                                  ].sort((a, b) => b.value - a.value)}
                                  cx="50%"
                                  cy="50%"
                                  innerRadius={60}
                                  outerRadius={80}
                                  fill="#4b8fe2"
                                  dataKey="value"
                                  onMouseEnter={onPieEnter}
                                />
                              </PieChart>
                            </ResponsiveContainer>
                          </div>
                        </Col>
                      </Row> */}
                    </div>
                  </div>
                )}
                {showGraph == "B" && (
                  <div className="tableContainer_analysis">
                    <div>
                      <ResponsiveContainer minHeight={300}>
                        <ComposedChart data={data_boxPlot[0]}>
                          <CartesianGrid strokeDasharray="3 3" />
                          <Bar stackId={"a"} dataKey={"min"} fill={"none"} />
                          <Bar
                            stackId={"a"}
                            dataKey={"bar"}
                            shape={<HorizonBar />}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"bottomWhisker"}
                            shape={<DotBar />}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"bottomBox"}
                            fill={"#8884d8"}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"bar"}
                            shape={<HorizonBar />}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"topBox"}
                            fill={"#8884d8"}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"topWhisker"}
                            shape={<DotBar />}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"bar"}
                            shape={<HorizonBar />}
                          />
                          <ZAxis
                            type="number"
                            dataKey="size"
                            range={[0, 250]}
                          />
                          {/* 
                                <Scatter
                                  dataKey="average"
                                  fill={"red"}
                                  stroke={"#FFF"}
                                /> */}
                          <XAxis dataKey="name" />
                          <YAxis
                            domain={[0, 1]}
                            tickFormatter={formatYAxisTick}
                          />
                        </ComposedChart>
                      </ResponsiveContainer>
                      <ResponsiveContainer minHeight={300}>
                        <ComposedChart data={data_boxPlot[1]}>
                          <CartesianGrid strokeDasharray="3 3" />
                          <Bar stackId={"a"} dataKey={"min"} fill={"none"} />
                          <Bar
                            stackId={"a"}
                            dataKey={"bar"}
                            shape={<HorizonBar />}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"bottomWhisker"}
                            shape={<DotBar />}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"bottomBox"}
                            fill={"#8884d8"}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"bar"}
                            shape={<HorizonBar />}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"topBox"}
                            fill={"#8884d8"}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"topWhisker"}
                            shape={<DotBar />}
                          />
                          <Bar
                            stackId={"a"}
                            dataKey={"bar"}
                            shape={<HorizonBar />}
                          />
                          <ZAxis
                            type="number"
                            dataKey="size"
                            range={[0, 250]}
                          />
                          {/* <Scatter
                                  dataKey="average"
                                  fill={"red"}
                                  stroke={"#FFF"}
                                /> */}
                          <XAxis dataKey="name" />
                          <YAxis
                            domain={[0, 1]}
                            tickFormatter={formatYAxisTick}
                          />
                        </ComposedChart>
                      </ResponsiveContainer>
                    </div>
                  </div>
                )}
                {showGraph == "C" && (
                  <div className="tableContainer_analysis">
                    {/* {showInfo2 && (
                  <Alert
                    variant="dark"
                    className="alert_dataInformation"
                    onClose={() => setShowInfo2(false)}
                    dismissible
                  >
                    <Alert.Heading>ABOUT THE DATA</Alert.Heading>
                    <p>In this section, ..</p>
                  </Alert>
                )} */}
                    <div style={{ width: "100%", height: 300 }}>
                      <ResponsiveContainer width="100%" height="100%">
                        <BarChart
                          width={500}
                          height={300}
                          data={data_barchart_SMD2}
                          margin={{
                            top: 20,
                            right: 30,
                            left: 20,
                            bottom: 5,
                          }}
                        >
                          <CartesianGrid strokeDasharray="3 3" />
                          <XAxis dataKey="name" />
                          <YAxis />
                          <Tooltip />
                          <Legend />
                          <Bar dataKey="Democrats" fill="blue" />
                          <Bar dataKey="Republicans" fill="red" />
                        </BarChart>
                      </ResponsiveContainer>
                    </div>
                    <div style={{ width: "100%", height: 300 }}>
                      <ResponsiveContainer width="100%" height="100%">
                        <BarChart
                          width={500}
                          height={300}
                          data={data_barchart_MMD2}
                          margin={{
                            top: 20,
                            right: 30,
                            left: 20,
                            bottom: 5,
                          }}
                        >
                          <CartesianGrid strokeDasharray="3 3" />
                          <XAxis dataKey="name" />
                          <YAxis />
                          <Tooltip />
                          <Legend />
                          <Bar dataKey="Democrats" fill="blue" />
                          <Bar dataKey="Republicans" fill="red" />
                        </BarChart>
                      </ResponsiveContainer>
                    </div>
                  </div>
                )}
                {showGraph == "D" && (
                  <div className="tableContainer_analysis">
                    <div style={{ width: "100%", height: 300 }}>
                      <ResponsiveContainer width="100%" height="100%">
                        <LineChart
                          data={data_curve1}
                          margin={{
                            top: 5,
                            right: 30,
                            left: 20,
                            bottom: 5,
                          }}
                        >
                          <CartesianGrid strokeDasharray="3 3" />
                          <XAxis
                            domain={[0, 1]}
                            tickFormatter={(tick) => {
                              return `${(
                                (tick * 100) /
                                (data_curve1.length - 1)
                              ).toFixed(0)}%`;
                            }}
                          />
                          <YAxis
                            domain={[0, 1]}
                            tickFormatter={formatYAxisTick}
                          />
                          <Tooltip />
                          <Legend />
                          <Line
                            type="monotone"
                            dataKey="Democrats"
                            stroke="blue"
                            activeDot={{ r: 8 }}
                          />
                          <Line
                            type="monotone"
                            dataKey="Republicans"
                            stroke="red"
                          />
                        </LineChart>
                      </ResponsiveContainer>
                    </div>
                    {/* </td>
                      <td> */}
                    <div style={{ width: "100%", height: 300 }}>
                      <ResponsiveContainer width="100%" height="100%">
                        <LineChart
                          data={[
                            {
                              Republicans: 0,
                              Democrats: 0,
                            },
                            {
                              Republicans: 1,
                              Democrats: 1,
                            },
                          ]}
                          margin={{
                            top: 5,
                            right: 30,
                            left: 20,
                            bottom: 5,
                          }}
                        >
                          <CartesianGrid strokeDasharray="3 3" />
                          <XAxis
                            domain={[0, 1]}
                            tickFormatter={formatXAxisTick}
                          />
                          <YAxis
                            domain={[0, 1]}
                            tickFormatter={formatYAxisTick}
                          />
                          <Tooltip />
                          <Legend />
                          <Line
                            type="monotone"
                            dataKey="Democrats"
                            stroke="blue"
                            activeDot={{ r: 8 }}
                          />
                          <Line
                            type="monotone"
                            dataKey="Republicans"
                            stroke="red"
                          />
                        </LineChart>
                      </ResponsiveContainer>
                    </div>
                  </div>
                )}
              </Col>
            </Row>
          </Container>
        </div>
      </div>
    </>
  );
}

export default Analysis;