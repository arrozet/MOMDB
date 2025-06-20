<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upgrade to Pro | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class="page-background">
<jsp:include page="cabecera_user.jsp" />

<section class="hero is-info">
    <div class="hero-body">
        <div class="container has-text-centered">
            <p class="title">
                UPGRADE TO RECOMMENDER
            </p>
        </div>
    </div>
</section>

<section class="section">
    <div class="container">
        <div class="columns is-centered">
            <div class="column is-half">
                <div class="box has-background-light">
                    <h2 class="title is-4 has-text-centered">Advantages of being a Recommender</h2>
                    <div class="content">
                        <ul>
                            <li>You can recommend movies to other users.</li>
                            <li>You will get personalized movie recommendations.</li>
                        </ul>
                    </div>
                    <form method="POST" action="/user/perform-upgrade">
                        <div class="field">
                            <div class="control has-text-centered">
                                <button type="submit" class="button is-success is-large">
                                    <span class="icon">
                                        <i class="fas fa-dollar-sign"></i>
                                    </span>
                                    <span>100$</span>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

</body>
</html>