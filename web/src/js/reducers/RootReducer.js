import Actions from '../constants/ArticleActions';

const initialState = {
    articles: []
};

function rootReducer(state = initialState, action) {
    if (action.type == Action.ADD_ARTICLE) {
        return Object.assign({}, state, {articles: state.articles.concat(action.payload)});
    }
    return state;
};

export default rootReducer;
