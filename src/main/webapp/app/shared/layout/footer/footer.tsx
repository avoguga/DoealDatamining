import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content">
    <Row>
      <Col>
        <p className='footer-text'>© 2022</p>
      </Col>
      <Col>
        <p className='footer-text'>Facilita Doeal</p>
      </Col>
      <Col>
        <p className='footer-text'>Simplificando sua vida</p>
      </Col>
    </Row>
  </div>
);

export default Footer;
