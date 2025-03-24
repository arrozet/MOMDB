library(tidyverse)
library(dplyr)
library(stringr)
library(purrr)
library(jsonlite)
credits <- read.csv("tmdb_5000_credits.csv")

View(credits)

credits$crew

# Extraer trabajos únicos de la columna crew
trabajos_unicos <- credits$crew %>%
  map(~ fromJSON(.) %>% as_tibble()) %>%  # Convertir cada string JSON en un dataframe
  bind_rows() %>%                         # Unir todas las filas en un solo dataframe
  distinct(job) %>%                        # Obtener los valores únicos
  pull(job)                                # Convertir en vector

print(trabajos_unicos)
