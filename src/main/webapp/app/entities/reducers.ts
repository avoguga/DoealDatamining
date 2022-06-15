import diario from 'app/entities/diario/diario.reducer';
import servidor from 'app/entities/servidor/servidor.reducer';
import concessao from 'app/entities/concessao/concessao.reducer';
import pDF from 'app/entities/pdf/pdf.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  diario,
  servidor,
  concessao,
  pDF,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
