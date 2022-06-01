import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDiario } from 'app/shared/model/diario.model';
import { getEntities } from './diario.reducer';

export const Diario = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const diarioList = useAppSelector(state => state.diario.entities);
  const loading = useAppSelector(state => state.diario.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="diario-heading" data-cy="DiarioHeading">
        <Translate contentKey="doealDataminingApp.diario.home.title">Diarios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="doealDataminingApp.diario.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/diario/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="doealDataminingApp.diario.home.createLabel">Create new Diario</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {diarioList && diarioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="doealDataminingApp.diario.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.diario.dataPublicacao">Data Publicacao</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.diario.ano">Ano</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.diario.numero">Numero</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {diarioList.map((diario, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/diario/${diario.id}`} color="link" size="sm">
                      {diario.id}
                    </Button>
                  </td>
                  <td>
                    {diario.dataPublicacao ? <TextFormat type="date" value={diario.dataPublicacao} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{diario.ano}</td>
                  <td>{diario.numero}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/diario/${diario.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/diario/${diario.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/diario/${diario.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="doealDataminingApp.diario.home.notFound">No Diarios found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Diario;
