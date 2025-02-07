import axios from 'axios';
import baseUrl from './baseUrl';

const axiosClient = axios.create({
  baseURL: `${baseUrl.apiBaseUrl}`,

});


export default axiosClient;
