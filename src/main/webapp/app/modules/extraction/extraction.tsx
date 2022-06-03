import './extraction.scss';

import React from 'react';
import { Row, Col } from 'reactstrap';
import UploadFile from 'app/components/Upload/upload';

export const Extraction = () => {

  return (
    <Row>
      <Col className="pad">
        <p className="titleInput">Por favor, selecione um PDF </p>
        <UploadFile />
      </Col>
    </Row>
  );
};

export default Extraction;
