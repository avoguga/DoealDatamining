export interface IPDF {
  id?: number;
  pdfContentType?: string | null;
  pdf?: string | null;
}

export const defaultValue: Readonly<IPDF> = {};
