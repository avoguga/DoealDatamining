import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConcessao } from 'app/shared/model/concessao.model';
import { getEntities } from './concessao.reducer';

export const Concessao = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const concessaoList = useAppSelector(state => state.concessao.entities);
  const loading = useAppSelector(state => state.concessao.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="concessao-heading" data-cy="ConcessaoHeading">
        <Translate contentKey="doealDataminingApp.concessao.home.title">Concessaos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="doealDataminingApp.concessao.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/concessao/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="doealDataminingApp.concessao.home.createLabel">Create new Concessao</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {concessaoList && concessaoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="doealDataminingApp.concessao.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.concessao.dataAssinatura">Data Assinatura</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.concessao.portaria">Portaria</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.concessao.periodoAquisitivo">Periodo Aquisitivo</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.concessao.tempoAfastamento">Tempo Afastamento</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.concessao.dataInicio">Data Inicio</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.concessao.dataFinal">Data Final</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.concessao.servidor">Servidor</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.concessao.diario">Diario</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {concessaoList.map((concessao, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/concessao/${concessao.id}`} color="link" size="sm">
                      {concessao.id}
                    </Button>
                  </td>
                  <td>
                    {concessao.dataAssinatura ? (
                      <TextFormat type="date" value={concessao.dataAssinatura} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{concessao.portaria}</td>
                  <td>{concessao.periodoAquisitivo}</td>
                  <td>{concessao.tempoAfastamento}</td>
                  <td>
                    {concessao.dataInicio ? <TextFormat type="date" value={concessao.dataInicio} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {concessao.dataFinal ? <TextFormat type="date" value={concessao.dataFinal} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{concessao.servidor ? <Link to={`/servidor/${concessao.servidor.id}`}>{concessao.servidor.id}</Link> : ''}</td>
                  <td>{concessao.diario ? <Link to={`/diario/${concessao.diario.id}`}>{concessao.diario.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/concessao/${concessao.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/concessao/${concessao.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/concessao/${concessao.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="doealDataminingApp.concessao.home.notFound">No Concessaos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Concessao;
