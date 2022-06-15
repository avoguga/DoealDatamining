import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Diario from './diario';
import Servidor from './servidor';
import Concessao from './concessao';
import PDF from './pdf';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}diario`} component={Diario} />
        <ErrorBoundaryRoute path={`${match.url}servidor`} component={Servidor} />
        <ErrorBoundaryRoute path={`${match.url}concessao`} component={Concessao} />
        <ErrorBoundaryRoute path={`${match.url}pdf`} component={PDF} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
