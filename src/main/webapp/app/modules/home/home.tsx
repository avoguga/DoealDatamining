import './home.scss';

import React, { useState } from 'react';
import { Row, Col } from 'reactstrap';
import UploadFile from 'app/components/Upload/upload';

export const Home = () => {

  return (
    <Row>
      <Col md="4" className="pad">
        <p className="titleInput">Por favor, selecione um PDF </p>
        <UploadFile />
      </Col>
    </Row>
  );
};

export default Home;
