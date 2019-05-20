const ESCAPE = 27;                                /* keycode of the escape button */
const DEFAULT_ALT = "a picture of a bus stop";    /* default alt text */

const acctName = 'polarisImages'
const sas = '?sv=2018-03-28&ss=b&srt=sco&sp=rwdlac&se=2019-06-17T09:30:48Z&st=2019-05-20T01:30:48Z&spr=https,http&sig=UKXLk49SPrGAF7rXcW6y4NpzcxsX3YA2tHwKWAZJBXs%3D';
const blobUri = 'https://' + acctName + '.blob.core.windows.net';
const blobService = AzureStorage.Blob.createBlobServiceWithSas(blobUri, sas);
const container = 'images';
