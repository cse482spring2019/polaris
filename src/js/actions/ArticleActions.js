import Actions from "../constants/ArticleActions";

export function addAction(payload) {
    return {
        type: Actions.ADD_ARTICLE,
        payload
    };
};
