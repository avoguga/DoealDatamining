import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPDF } from 'app/shared/model/pdf.model';
import { getEntities } from './pdf.reducer';

export const PDF = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const pDFList = useAppSelector(state => state.pDF.entities);
  const loading = useAppSelector(state => state.pDF.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="pdf-heading" data-cy="PDFHeading">
        <Translate contentKey="doealDataminingApp.pDF.home.title">PDFS</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="doealDataminingApp.pDF.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/pdf/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="doealDataminingApp.pDF.home.createLabel">Create new PDF</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pDFList && pDFList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="doealDataminingApp.pDF.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="doealDataminingApp.pDF.pdf">Pdf</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pDFList.map((pDF, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pdf/${pDF.id}`} color="link" size="sm">
                      {pDF.id}
                    </Button>
                  </td>
                  <td>
                    {pDF.pdf ? (
                      <div>
                        {pDF.pdfContentType ? (
                          <a onClick={openFile(pDF.pdfContentType, pDF.pdf)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {pDF.pdfContentType}, {byteSize(pDF.pdf)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/pdf/${pDF.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/pdf/${pDF.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/pdf/${pDF.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="doealDataminingApp.pDF.home.notFound">No PDFS found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PDF;
