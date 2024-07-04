
const api = supertest('https://jsonplaceholder.typicode.com/');
import { describe } from "mocha";
import supertest from "supertest";
import { assert, expect, use } from 'chai';
import chaiSorted from 'chai-sorted';
use(chaiSorted);
import fs from 'fs'
const user = JSON.parse(fs.readFileSync('./testData/user.json', 'utf-8'));
const userChelseyJson = JSON.parse(fs.readFileSync('./testData/userChelsey.json', 'utf-8'));

describe('API Tests', () => {
  it('should make a GET api', async () => {
    const response = await api.get('/posts');

    expect(await response.status).is.equal(200)
    
    expect(await response.type).is.eq('application/json')

    const ids = response.body.map(item => item.id);

    expect(ids).to.be.sorted()
  });

  it('GET request to get post with id=99', async () => {
    const response = await api.get('/posts/99');

    expect(await response.status).is.equal(200)

    expect(response.body.userId).is.eq(10)
    expect(response.body.id).is.eq(99)
    expect(response.body.body).is.not.empty
  });

  it('Send GET request to get post with id=150 (/posts/150)', async () => {

    const response = await api.get('/posts/150');
    expect(await response.status).is.equal(404)

    expect(await response.body.body).is.undefined
  })

  it('Send POST request to create post with userId=1 and random body and random title', async () => {
    
    const response = await api.post('/posts').send(user)

    expect(await response.status).is.eq(201)
    expect(await response.body.id).is.eq(101)
    expect(await response.body.body).is.eq("example")
    expect(await response.body.userId).is.eq('1')
    expect(await response.body.titile).is.eq("FaraExample")
  });

  it('Send GET request to get users (/users)', async () => {
    const response = await api.get('/users');

    expect(await response.status).is.equal(200)

    expect(await response.type).is.eq('application/json')

    const userChelsey = await response.body.find(user => user.id === 5)

    expect(userChelsey).is.deep.equal(userChelseyJson)
  });

  it('Send GET request to get user with id=5', async () =>{
    const response = await api.get('/users/5')

    expect(await response.status).is.equal(200)

    expect(response.body).is.deep.eq(userChelseyJson)
  })
});
