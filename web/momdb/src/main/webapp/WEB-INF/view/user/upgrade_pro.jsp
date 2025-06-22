<%--
Author: amcgiluma (Juan Manuel Valenzuela)
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upgrade to Pro | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/upgrade_pro.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class="page-background">
<jsp:include page="cabecera_user.jsp" />

<section class="hero upgrade-hero">
    <div class="hero-body">
        <div class="container has-text-centered">
            <p class="title is-2">
                UPGRADE TO RECOMMENDER
            </p>
            <p class="subtitle is-5">
                Unlock exclusive features and become a key member of our community.
            </p>
        </div>
    </div>
</section>

<section class="section">
    <div class="container">
        <div class="columns is-centered">
            <div class="column is-two-thirds">
                <div class="box advantages-box">
                    <h2 class="title is-3 has-text-centered">Advantages of being a Recommender</h2>
                    <div class="content">
                        <div class="columns is-multiline is-vcentered has-text-centered">
                            <div class="column is-one-third">
                                <span class="icon is-large">
                                    <i class="fas fa-bullhorn"></i>
                                </span>
                                <p class="is-size-5 mt-2"><strong>Share your wisdom</strong> by recommending movies to thousands of users.</p>
                            </div>
                            <div class="column is-one-third">
                                <span class="icon is-large">
                                    <i class="fas fa-magic"></i>
                                </span>
                                <p class="is-size-5 mt-2">Discover hidden gems with our <strong>personalized movie recommendations</strong>.</p>
                            </div>
                            <div class="column is-one-third">
                                 <span class="icon is-large">
                                    <i class="fas fa-medal"></i>
                                </span>
                                <p class="is-size-5 mt-2">Receive a <strong>special badge</strong> on your profile.</p>
                            </div>
                        </div>
                    </div>
                    <form method="POST" action="/user/perform-upgrade" class="has-text-centered">
                        <div class="field">
                            <div class="control">
                                <button type="submit" class="button is-success is-large upgrade-button">
                                    <span class="icon">
                                        <i class="fas fa-rocket"></i>
                                    </span>
                                    <span>Upgrade now for 100$</span>
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