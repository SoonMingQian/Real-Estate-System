<template>
    <v-container>
      <h1 class="text-center">Help & FAQ</h1>
      <v-row>
        <v-col cols="12" sm="6" md="4" v-for="(item, i) in faqs" :key="i">
          <v-card class="mb-5">
            <v-card-title primary-title>
              {{ item.question }}
            </v-card-title>
            <v-card-text>
              {{ item.answer }}
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script>
  export default {
    name: "HelpPage",
    data() {
      return {
        faqs: [
        {
          question: "How do I list my property?",
          answer: "To list your property, please contact agent"
        },
        {
          question: "How do I edit my property listing?",
          answer: "Only agent can edit property listing. So please contact your agent."
        },
        {
          question: "How can I contact customer support?",
          answer: "You can reach our customer support team by email estateeagle2024@gmail.com."
        },
        {
          question: "What is the process for rent a property?",
          answer: "In the property, there is an enquiry button and it will automatically send an email to the agent."
        },
      ]
      }
    }
  }
  </script>