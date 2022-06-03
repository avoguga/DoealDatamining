import React, { useState } from 'react';
import './upload.scss';

const UploadFile = () => {
    const [files, setFiles]: any = useState('');
    //state for checking file size
    const [fileSize, setFileSize] = useState(true);
    // for file upload progress message
    const [fileUploadProgress, setFileUploadProgress] = useState(false);
    //for displaying response message
    const [fileUploadResponse, setFileUploadResponse] = useState(null);
    //base end point url
    const FILE_UPLOAD_BASE_ENDPOINT = "http://localhost:8000";

    const uploadFileHandler = (event) => {
        setFiles(event.target.files);
       };

      const fileSubmitHandler = (event) => {
       event.preventDefault();
       setFileSize(true);
       setFileUploadProgress(true);
       setFileUploadResponse(null);

        const formData = new FormData();
    
        for (let i = 0; i < files.length; i++) {
            if (files[i].size > 99999){
                setFileSize(false);
                setFileUploadProgress(false);
                setFileUploadResponse(null);
                return;
            }

            formData.append(`files`, files[i])
        }

        const requestOptions = {
            method: 'POST',
            body: formData
        };
        fetch(FILE_UPLOAD_BASE_ENDPOINT+'/arquivos', requestOptions)
            .then(async response => {
                const isJson = response.headers.get('content-type')?.includes('multipart/form-data');
                const data = isJson && await response.json();

                // check for error response
                if (!response.ok) {
                    // get error message
                    const error = (data && data.message) || response.status;
                    setFileUploadResponse(data.message);
                    return Promise.reject(error);
                }

               console.log(data.message);
               setFileUploadResponse(data.message);
            })
            .catch(error => {
                console.error('Error while uploading file!', error);
            });
        setFileUploadProgress(false);
      };

    return(

      <form onSubmit={fileSubmitHandler}>
         <input className="inputFile" type="file" name='files' multiple onChange={uploadFileHandler}/>
         <br />
         <button className='buttonUpload' type='submit'>Upload</button>
         {!fileSize && <p style={{color:'red'}}>O tamanho do arquivo é muito grande!</p>}
         {fileUploadProgress && <p style={{color:'red'}}>Arquivo(s) sendo enviado(s)</p>}
        {fileUploadResponse!=null && <p style={{color:'green'}}>{fileUploadResponse}</p>}
      </form>

    );
}
export default UploadFile;