import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './concessao.reducer';

export const ConcessaoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const concessaoEntity = useAppSelector(state => state.concessao.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="concessaoDetailsHeading">
          <Translate contentKey="doealDataminingApp.concessao.detail.title">Concessao</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{concessaoEntity.id}</dd>
          <dt>
            <span id="dataAssinatura">
              <Translate contentKey="doealDataminingApp.concessao.dataAssinatura">Data Assinatura</Translate>
            </span>
          </dt>
          <dd>
            {concessaoEntity.dataAssinatura ? (
              <TextFormat value={concessaoEntity.dataAssinatura} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="portaria">
              <Translate contentKey="doealDataminingApp.concessao.portaria">Portaria</Translate>
            </span>
          </dt>
          <dd>{concessaoEntity.portaria}</dd>
          <dt>
            <span id="periodoAquisitivo">
              <Translate contentKey="doealDataminingApp.concessao.periodoAquisitivo">Periodo Aquisitivo</Translate>
            </span>
          </dt>
          <dd>{concessaoEntity.periodoAquisitivo}</dd>
          <dt>
            <span id="tempoAfastamento">
              <Translate contentKey="doealDataminingApp.concessao.tempoAfastamento">Tempo Afastamento</Translate>
            </span>
          </dt>
          <dd>{concessaoEntity.tempoAfastamento}</dd>
          <dt>
            <span id="dataInicio">
              <Translate contentKey="doealDataminingApp.concessao.dataInicio">Data Inicio</Translate>
            </span>
          </dt>
          <dd>
            {concessaoEntity.dataInicio ? (
              <TextFormat value={concessaoEntity.dataInicio} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dataFinal">
              <Translate contentKey="doealDataminingApp.concessao.dataFinal">Data Final</Translate>
            </span>
          </dt>
          <dd>
            {concessaoEntity.dataFinal ? <TextFormat value={concessaoEntity.dataFinal} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="doealDataminingApp.concessao.servidor">Servidor</Translate>
          </dt>
          <dd>{concessaoEntity.servidor ? concessaoEntity.servidor.id : ''}</dd>
          <dt>
            <Translate contentKey="doealDataminingApp.concessao.diario">Diario</Translate>
          </dt>
          <dd>{concessaoEntity.diario ? concessaoEntity.diario.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/concessao" replace color="secondary" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/concessao/${concessaoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConcessaoDetail;
